package com.ea.card.crm.service.msghandler;

import com.ea.card.crm.constants.IntegralConstants;
import com.ea.card.crm.dao.WxActiveMessageDao;
import com.ea.card.crm.dao.impl.WxActiveMessageDaoImpl;
import com.ea.card.crm.facade.response.GetIntegralData;
import com.ea.card.crm.facade.response.GetIntegralResult;
import com.ea.card.crm.model.*;
import com.ea.card.crm.service.*;
import com.ea.card.crm.service.exception.ActiveMemberCardException;
import com.ea.card.crm.service.vo.*;
import com.lmtech.common.ContextManager;
import com.lmtech.common.StateResult;
import com.lmtech.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 接收卡片消息处理
 */
@Service
@RefreshScope
public class ReceiveCardMessageHandler implements MessageHandler {

    public String URL_WX_ACTIVE_CARD = "https://api.weixin.qq.com/card/membercard/activate?access_token=";

    @Value("${card.active.active_present_bonus}")
    private long ACTIVE_PRESENT_BONUS;        //激活送100积分
    @Value("${card.active.inner_user_present_card_count}")
    private int INNER_USER_PRESENT_CARD_COUNT;    //内部员工赠送卡片数
    @Value("${card.active.inner_user_present_card_month}")
    private int INNER_USER_PRESENT_CARD_MONTH;    //内部员工赠送卡片有效月份
    @Value("${card.active.present_vpass_card_count}")
    private int PRESENT_VPASS_CARD_COUNT;     //Vpass卡赠送得到的副卡张数
    @Value("${card.active.present_vpass_card_month}")
    private int PRESENT_VPASS_CARD_MONTH;    //Vpass卡赠送得到的副卡有效月份

    @Value("${service.url_account_mybalance}")
    public String URL_ACCOUNT_MYBALANCE;//获取余额

    @Value("${service.url_account_addwxrechargemoney}")
    private String URL_ACCOUNT_ADDWXRECHARGEMONEY;//充值，更新余额

    //vpass会员 微信卡面背景图片
    @Value("${member_card.vpass_wx_bg}")
    private String VPASS_WX_BG;

    //普通会员 微信 卡面背景图片
    @Value("${member_card.pass_wx_bg}")
    private String PASS_WX_BG;

    //vpass会员 微信卡面背景图片
    @Value("${member_card.vpass_h5_bg}")
    private String VPASS_H5_BG;

    //普通会员 微信 卡面背景图片
    @Value("${member_card.pass_h5_bg}")
    private String PASS_H5_BG;

    @Autowired
    private CardActiveRecordService cardActiveRecordService;
    @Autowired
    private MemberRegisterService memberRegisterService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private GiftMemberCardService giftMemberCardService;
    @Autowired
    private IntegralService integralService;
    @Autowired
    private CardCategoryService cardCategoryService;
    @Autowired
    private CardPresentRecordService cardPresentRecordService;
    @Autowired
    private CodeAdaptorService codeAdaptorService;
    @Autowired
    private WxService wxService;
    @Autowired
    private WxActiveMessageService wxActiveMessageService;

    @Override
    public void handle(WxMessageBase message) {
        WxReceiveCardMessage receiveCardMessage = (WxReceiveCardMessage) message;
        String tid = IdWorkerUtil.generateStringId();
        String code = receiveCardMessage.getUserCardCode();
        //获取激活记录
        CardActiveRecord record;
        MemberRegister memberRegister = null;
        //是否首次领卡
        boolean isFirst = false;

        OutStrParam param = null;
        if (receiveCardMessage.isRestoreMemberCard() == 0) {
            //首次领卡
        	isFirst = true;
        	saveWxActiveMessage(receiveCardMessage);
            String officialOpenId;
            if (!StringUtil.isNullOrEmpty(receiveCardMessage.getOuterStr())) {
                String jsonStr = UrlUtil.decodeUrl(receiveCardMessage.getOuterStr());
                param = (OutStrParam) JsonUtil.fromJson(jsonStr, OutStrParam.class);
            } else {
                LoggerManager.warn("从公众号或其他未知方式首次领卡激活，未传入有效OuterStr，本次激活取消");
                return;
            }

            if (!StringUtil.isNullOrEmpty(param.getAppletOpenId())) {
                officialOpenId = param.getAppletOpenId();
            } else {
                officialOpenId = receiveCardMessage.getFromUserName();
            }
            record = cardActiveRecordService.getAppliedRecordByOpenId(officialOpenId);
        } else {
            //删除再领卡
            memberRegister = memberRegisterService.getByCodeAndIsDelete(code, MemberRegister.DELETE_YES);
            if (memberRegister != null) {
                String uniqueId = memberRegister.getUniqueId();
                record = cardActiveRecordService.getAppliedRecordByUniqueId(uniqueId);
                //取旧的用户id，保证领卡多次用户id还是一致
                record.setUserId(memberRegister.getUserId());
            } else {
                throw new ActiveMemberCardException("微信卡删除,再次激活code[" + code + "]数据不存在！");
            }
            saveWxActiveMessage(receiveCardMessage);

        }
        ContextManager.setValue("card_active_record", record);

        if (record != null) {
        	if(param==null) {
        		//构建outstr参数
                String jsonStr = record.getOutstr();
                param = (OutStrParam) JsonUtil.fromJson(jsonStr, OutStrParam.class);
        	}
            //积分
            long activePresentBonus = 0L;
            //余额
            double myBalance = 0L;
            //新增余额
            double newBalance = 0L;
            //是否激活过会员卡
            boolean isRegisterCard = false;
            //根据code查找用户是否存在逻辑删除
            if (memberRegister == null) {
                memberRegister = memberRegisterService.getByCodeAndIsDelete(code, MemberRegister.DELETE_YES);
            }
            if (memberRegister == null) {
                //未激活过会员卡
                activePresentBonus = ACTIVE_PRESENT_BONUS;
            } else {
                isRegisterCard = true;
                myBalance = getMyBalance(tid, memberRegister.getUserId(), memberRegister.getPhone());
                activePresentBonus = getActivePresentBonus(memberRegister.getUserId(), activePresentBonus);
            }
            String cardId = receiveCardMessage.getCardId();
            if (param == null) {
                LoggerManager.info("1.激活微信卡 => 开始");
//                myBalance += record.getBalance();
                activeWxCard(code, cardId, record.getBalance(), record, activePresentBonus);
                LoggerManager.info("1.激活微信卡 => 结束");
            } else {
                LoggerManager.info("outstr-json:" + record.getOutstr());
                if (param.getActionType() == OutStrParam.ACTION_TYPE_ACTIVE) {
                    LoggerManager.info("2.激活微信卡 => 开始");
//                    myBalance += record.getBalance();
//                    newBalance = record.getBalance();
                    activeWxCard(code, cardId, record.getBalance(), record, activePresentBonus);
                    LoggerManager.info("2.激活微信卡 => 结束");
                } else if (param.getActionType() == OutStrParam.ACTION_TYPE_PRESENT || param.getActionType() == OutStrParam.ACTION_TYPE_SHARE) {
                    //送礼品卡激活｜分享激活
                    GiftMemberCard giftMemberCard = giftMemberCardService.get(param.getGiftCardId());
                    myBalance += giftMemberCard.getBalance();
                    newBalance = giftMemberCard.getBalance();
                    giftMemberCard.setRecvOpenId(record.getUniqueId());
                    giftMemberCard.setRecvDate(new Date());
                    giftMemberCard.setRecvNickname(record.getNickname());
                    giftMemberCard.setRecvHeadimgurl(record.getHeadimgurl());
                    giftMemberCard.setRecvPhone(record.getPhone());
                    giftMemberCard.setRecvUserId(record.getUserId());
                    giftMemberCard.setStatus(GiftMemberCard.STATUS_RECEIVED);
//                    giftMemberCard.setBalance(myBalance);//TODO 是否累加余额到礼品卡
                    giftMemberCardService.edit(giftMemberCard);

                    Date endDate = DateUtil.addMonth(new Date(), giftMemberCard.getValidMonth());
                    LoggerManager.info("3.激活微信卡 => 开始");
                    activeWxCardPresent(code, cardId, myBalance, giftMemberCard.getCardLevel(), endDate, record, activePresentBonus, param.getActionType(), giftMemberCard.getCardCategoryId());
                    LoggerManager.info("3.激活微信卡 => 结束");

                    //解锁当前用户锁定的其他卡
                    giftMemberCardService.unlockByList(record.getOpenId());

                    //检测批次内的卡是否已领完
                    List<CardPresentRecord> list = cardPresentRecordService.selectByGiftCardIdAndStatus(param.getGiftCardId(), CardPresentRecord.STATUS_NEW);
                    if (list == null || list.size() < 1) {
                        throw new ActiveMemberCardException("不存在礼品卡对应的批次记录");
                    }
                    CardPresentRecord cardPresentRecord = list.get(0);
//                    CardPresentRecord cardPresentRecord = cardPresentRecordService.get(request.getPresentRecordId());
                    List<String> giftCardIds = StringUtil.splitString(cardPresentRecord.getGiftCardIds());
                    boolean existWaitReceive = giftMemberCardService.existWaitReceiveCards(giftCardIds);
                    if (!existWaitReceive) {
                        //不存在待领取卡片，结束批次
                        cardPresentRecordService.updateStatus(cardPresentRecord.getId(), CardPresentRecord.STATUS_FINISH);
                    }
                } else {
                    LoggerManager.warn("未知的ActionType:" + param.getActionType());
                }
            }
            record.setStatus(CardActiveRecord.STATUS_ACTIVE);
            cardActiveRecordService.edit(record);
            recordRegister(record,param);


            //领卡方：没有激活过，则领取100积分
            if (!isRegisterCard&&isFirst) {
                LoggerManager.debug("领卡方：没有激活过，则领取100积分");
                integralService.increaseIntegral(record.getUserId(), ACTIVE_PRESENT_BONUS, IntegralConstants.SOURCE_SIX);
                LoggerManager.debug("领卡方userId：" + record.getUserId() + ",领取100积分");
            }
            //通知交易更新余额
            if (newBalance > 0) {
                updMyBalance(tid, record.getPhone(), record.getUserId(), newBalance);
            }

            //赠卡方 ：非自主激活，则领取100积分；  新增积分次数限制  
            if (param.getActionType() == OutStrParam.ACTION_TYPE_PRESENT || param.getActionType() == OutStrParam.ACTION_TYPE_SHARE) {
                if (!StringUtil.isNullOrEmpty(param.getOwnerOpenId())) {
                    MemberRegister mr = memberRegisterService.getByOpenId(param.getOwnerOpenId());
                    if (mr != null && integralService.sendCardCallBack(mr.getUserId())) {
                    	
                        LoggerManager.debug("赠卡方 ：非自主激活，则领取100积分");
                        integralService.increaseIntegral(mr.getUserId(), ACTIVE_PRESENT_BONUS, IntegralConstants.ONE);
                        LoggerManager.debug("赠卡方userId：" + mr.getUserId() + ",领取100积分");
                    } else {
                        LoggerManager.warn("用户openId:{" + param.getOwnerOpenId() + "},数据未知异常或已超过10次领取次数。");
                    }
                } else {
                    LoggerManager.warn("用户userId:{" + record.getUserId() + "},数据异常未匹配到赠卡方的openId。");
                }
            }

        } else {
            //根据openid查找用户是否存在逻辑删除
//            MemberRegister memberRegister = memberRegisterService.selectByOfficialOpenIdAndIsDelete(receiveCardMessage.getFromUserName(), MemberRegister.DELETE_YES);
            if (memberRegister == null) {
                memberRegister = memberRegisterService.getByCodeAndIsDelete(code, MemberRegister.DELETE_YES);
            }

            if (memberRegister == null) {
                LoggerManager.error("未查找到申请卡片记录。");
            } else {
                LoggerManager.info("卡逻辑注册。");
                memberRegisterService.updateIsDelete(memberRegister.getId(), MemberRegister.DELETE_NO);
            }
        }
    }

    private void activeWxCard(String code, String cardId, double balance, CardActiveRecord record, long activePresentBonus) {
        //激活微信会员卡
        WxActiveCardRequest activeCardRequest = new WxActiveCardRequest();
        activeCardRequest.setCard_id(cardId);
        activeCardRequest.setCode(code);
        activeCardRequest.setInit_balance(balance*100);//单位:分
        activeCardRequest.setInit_bonus(activePresentBonus);
        activeCardRequest.setMembership_number(code);
        int mLevel;
        String mLevelName;
        String backgroundUrl;
        String cardBackGround;
        if (MemberRegister.USER_TYPE_INNER.equalsIgnoreCase(record.getUserType())) {
            mLevel = MemberRegister.MLEVEL_VPASS;
            mLevelName = codeAdaptorService.getNameByCodeItemValue(String.valueOf(MemberRegister.MLEVEL_VPASS));
            backgroundUrl = VPASS_WX_BG;
            cardBackGround = VPASS_H5_BG;
        } else {
            mLevel = MemberRegister.MLEVEL_NORMAL;
            mLevelName = codeAdaptorService.getNameByCodeItemValue(String.valueOf(MemberRegister.MLEVEL_NORMAL));
            backgroundUrl = PASS_WX_BG;
            cardBackGround = PASS_H5_BG;
        }
        LoggerManager.debug("mLevelName:" + mLevelName + ",mLevel:" + mLevel + ",微信BG:" + backgroundUrl + ",h5BG:" + cardBackGround);
        activeCardRequest.setInit_custom_field_value1(mLevelName);
        activeCardRequest.setBackground_pic_url(backgroundUrl);
        String accessToken = wxService.getAccessToken();
        String url = URL_WX_ACTIVE_CARD + accessToken;
        WxApiResponse activeCardResponse = restTemplate.postForObject(url, activeCardRequest, WxApiResponse.class);
        if (!activeCardResponse.isSuccess()) {
            throw new ActiveMemberCardException("激活微信卡失败,errorcode:" + activeCardResponse.getErrcode() + ",errmsg:" + activeCardResponse.getErrmsg());
        }

        record.setCardLevel(mLevel);
        record.setCardId(cardId);
        record.setCode(code);
        record.setBonus(activePresentBonus);
        record.setMembershipNum(code);
        record.setCardBackgorund(cardBackGround);
    }

    private void activeWxCardPresent(String code, String cardId, double balance, int cardLevel, Date endDate, CardActiveRecord record, long activePresentBonus, int actionType, String cardCategoryId) {
        //激活微信会员卡
        WxActiveCardRequest activeCardRequest = new WxActiveCardRequest();
        activeCardRequest.setCard_id(cardId);
        activeCardRequest.setCode(code);
        activeCardRequest.setInit_balance(balance*100);//单位:分
        activeCardRequest.setInit_bonus(activePresentBonus);
        activeCardRequest.setMembership_number(code);
        String mLevelName = codeAdaptorService.getNameByCodeItemValue(String.valueOf(cardLevel));

        activeCardRequest.setInit_custom_field_value1(mLevelName);
        String accessToken = wxService.getAccessToken();
        String url = URL_WX_ACTIVE_CARD + accessToken;
        String backgroundUrl;
        String cardBackground;

        if (actionType == OutStrParam.ACTION_TYPE_PRESENT) {
            //赠送礼品卡
            CardCategory cardCategory = cardCategoryService.get(cardCategoryId);
            backgroundUrl = cardCategory.getWx_background();
            cardBackground = cardCategory.getBackground();
        } else {
            //会员推荐
            if (MemberRegister.MLEVEL_VPASS == cardLevel) {
                //vpass会员
                backgroundUrl = VPASS_WX_BG;
                cardBackground = VPASS_H5_BG;
            } else {
                //普通会员
                backgroundUrl = PASS_WX_BG;
                cardBackground = PASS_H5_BG;
            }
        }
        LoggerManager.debug("mLevelName:" + mLevelName + ",mLevel:" + cardLevel + ",微信BG:" + backgroundUrl + ",h5BG:" + cardBackground);
        activeCardRequest.setBackground_pic_url(backgroundUrl);
        WxApiResponse activeCardResponse = restTemplate.postForObject(url, activeCardRequest, WxApiResponse.class);
        if (!activeCardResponse.isSuccess()) {
            throw new ActiveMemberCardException("激活微信卡失败,errorcode:" + activeCardResponse.getErrcode() + ",errmsg:" + activeCardResponse.getErrmsg());
        }
        record.setEndDate(endDate);
        record.setCardLevel(cardLevel);
        record.setCardId(cardId);
        record.setCode(code);
        record.setBonus(activePresentBonus);
        record.setMembershipNum(code);
        record.setCardBackgorund(cardBackground);
    }

    private void recordRegister(CardActiveRecord record, OutStrParam param) {
        MemberRegister register = new MemberRegister();
        register.setUserId(record.getUserId());
        register.setUserType(record.getUserType());
        register.setAppType(record.getAppType());
        register.setCardId(record.getCardId());
        register.setCode(record.getCode());
        register.setOpenId(record.getUniqueId());
        register.setUniqueId(record.getUniqueId());
        register.setOfficialOpenId(record.getOpenId());
        register.setNickname(record.getNickname());
        register.setHeadimgurl(record.getHeadimgurl());
        register.setSex(record.getSex());
        register.setPhone(record.getPhone());
        register.setMembershipNum(record.getCode());
        register.setAuthRefreshToken(record.getWapToken());
        register.setBeginDate(new Date());
        register.setCardBackground(record.getCardBackgorund());
        register.setIntegral((int) record.getBonus());
        if (MemberRegister.USER_TYPE_INNER.equalsIgnoreCase(record.getUserType())&&param.getActionType() == OutStrParam.ACTION_TYPE_ACTIVE) {
            //自主激活领卡的内部用户，送积分、等级
            register.setmLevel(MemberRegister.MLEVEL_VPASS);
            register.setEndDate(DateUtil.parse("9999-00-00"));
            register.setForever(true);

            //内部员工激活赠送卡片
            addGiftCard(register,INNER_USER_PRESENT_CARD_MONTH,param,true);

        } else {
            register.setmLevel(record.getCardLevel());
            if (record.getEndDate() != null) {
                register.setEndDate(record.getEndDate());
                register.setForever(false);
            } else {
                register.setEndDate(DateUtil.parse("9999-00-00"));
                register.setForever(true);
            }

            //Vpass卡赠送时自成生成10张半年的Vpass礼品会员卡
            if (record.getCardLevel() == MemberRegister.MLEVEL_VPASS) {
            	addGiftCard(register,PRESENT_VPASS_CARD_MONTH,param,false);

            }
        }
        LoggerManager.debug("注册卡信息：" + JsonUtil.toJson(register));
        memberRegisterService.add(register);
    }

    /**
     * 累计积分
     *
     * @param userId
     * @param activePresentBonus
     * @return
     */
    public long getActivePresentBonus(String userId, long activePresentBonus) {
        //查询用户当前积分
        GetIntegralResult integralResult = integralService.getHistoryIntegral(userId);
        if (integralResult.getState() != 0)
            throw new ActiveMemberCardException(integralResult.getMsg(), integralResult.getState());
        GetIntegralData integralData = integralResult.getData();
        if (integralData != null) {
            activePresentBonus += integralData.getSumIntegralNumber();
        }
        return activePresentBonus;
    }

    /**
     * 查询余额
     *
     * @param tid
     * @param userId
     * @param phone
     * @return
     */
    public double getMyBalance(String tid, String userId, String phone) {
        // 游物欧品帐户存在，查询余额
        /*MultiValueMap<String, Object> balanceMap = new LinkedMultiValueMap<String, Object>();
        balanceMap.save("tid", tid);
        balanceMap.save("userid", userId);
        balanceMap.save("phone", phone);
        balanceMap.save("md5str", MD5Util.getMD5String("wallet" + tid + userId + phone));
        HttpEntity<MultiValueMap<String, Object>> balanceRequest = new HttpEntity<MultiValueMap<String, Object>>(balanceMap, null);
        GetMyBalanceResult balanceResult = restTemplate.postForObject(URL_ACCOUNT_MYBALANCE, balanceRequest, GetMyBalanceResult.class);
        double balance = 0L;
        if (balanceResult.getState() == 0) {
            balance = balanceResult.getMyBalance();
        }
        return balance;*/
        return 0;
    }

    /**
     * 更新余额
     *
     * @param tid
     * @param phone
     * @param userId
     * @param money
     */
    public void updMyBalance(String tid, String phone, String userId, double money) {
        /*MultiValueMap<String, Object> balanceMap = new LinkedMultiValueMap<String, Object>();
        balanceMap.save("tid", tid);
        balanceMap.save("phone", phone);
        balanceMap.save("userid", userId);
        balanceMap.save("money", money);
        String md5Str = MD5Util.getMD5String("wallet" + tid + userId + phone + money);
        balanceMap.save("md5str", md5Str);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(balanceMap, null);
        LoggerManager.debug("url:" + URL_ACCOUNT_ADDWXRECHARGEMONEY + ",request:" + JsonUtil.toJson(request));
        StateResult result = restTemplate.postForObject(URL_ACCOUNT_ADDWXRECHARGEMONEY, request, StateResult.class);

        if (result.isSuccess()) {
            LoggerManager.debug("userId" + balanceMap.get("userid") + "余额,更新[" + money + "]成功！");
        } else {
            LoggerManager.debug("userId" + balanceMap.get("userid") + "余额,更新[" + money + "]失败！");
        }*/
    }
    
    /**
     * 首次激活记录 微信卡code和微信公众号openid
     * @param receiveCardMessage
     */
    public void saveWxActiveMessage(WxReceiveCardMessage receiveCardMessage) {
    	if(wxActiveMessageService.getByCode(receiveCardMessage.getUserCardCode()) == null)
    		wxActiveMessageService.saveWxActiveMessage(receiveCardMessage.getUserCardCode(), receiveCardMessage.getFromUserName());

    }
    
    /**
     * 是否存在礼品卡，不存在则新增，存在则不新增
     * @param register
     * @param month
     * @param param
     * @param isItActive 是否自主激活
     */
    public void addGiftCard(MemberRegister register, int month, OutStrParam param,boolean isItActive) {
    	if(param.getActionType() == OutStrParam.ACTION_TYPE_PRESENT || param.getActionType() == OutStrParam.ACTION_TYPE_SHARE) {
    		LoggerManager.debug("param:"+JsonUtil.toJson(param));
    		if(!StringUtil.isNullOrEmpty(param.getGiftCardId())) {
    			GiftMemberCard giftCard = giftMemberCardService.getById(param.getGiftCardId());
    			//是否存在礼品卡
    	    	int count = giftMemberCardService.isExitsCard(register.getOpenId());
    	    	//不存在礼品卡且必须为大于6个月的VPASS卡
    	    	if(count==0&&giftCard.getValidMonth()>6) {
    	    		batchAddGitCard(register, month);
    	    	}else {
    	    		LoggerManager.debug("没有赠送的礼品卡记录！");
    	    	}
    		}else {
    			LoggerManager.debug("礼品卡不存在！");
    		}
    	}else if(param.getActionType() == OutStrParam.ACTION_TYPE_ACTIVE && isItActive) {//内部员工自主激活
    		batchAddGitCard(register, month);
    	}else {
    		LoggerManager.debug("用户条件不满足，不赠送礼品卡！");
    	}
    }
    
    /**
     * 批量添加礼品卡
     * @param register
     * @param month
     */
    public void batchAddGitCard(MemberRegister register, int month) {
    	List<GiftMemberCard> giftMemberCardList = new ArrayList<>();
        for (int i = 0; i < PRESENT_VPASS_CARD_COUNT; i++) {
            GiftMemberCard giftMemberCard = new GiftMemberCard();
            giftMemberCard.setId(IdWorkerUtil.generateStringId());
            giftMemberCard.setUserId(register.getUserId());
            giftMemberCard.setStatus(GiftMemberCard.STATUS_NEW);
            giftMemberCard.setOpenId(register.getOpenId());
            giftMemberCard.setPhone(register.getPhone());
            giftMemberCard.setSource(GiftMemberCard.SOURCE_INNER_PRESENT);
            giftMemberCard.setCardBackground(register.getCardBackground());
            giftMemberCard.setValidMonth(month);
            giftMemberCard.setCardLevel(MemberRegister.MLEVEL_VPASS);
            giftMemberCard.setBalance(0);
            giftMemberCard.setCreateDate(new Date());
            giftMemberCard.setUpdateDate(new Date());
            giftMemberCard.setDataVersion(1);
            giftMemberCardList.add(giftMemberCard);
        }
        giftMemberCardService.insertGiftMemberCardBatch(giftMemberCardList);
    }
}
