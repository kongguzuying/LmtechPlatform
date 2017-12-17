package com.ea.card.crm.service.impl;

import com.ea.card.crm.constants.IntegralConstants;
import com.ea.card.crm.dao.IntegralSetDao;
import com.ea.card.crm.dao.IntegralSignLogDao;
import com.ea.card.crm.facade.response.*;
import com.ea.card.crm.model.*;
import com.ea.card.crm.service.*;
import com.ea.card.crm.service.exception.IntegralConsumeException;
import com.ea.card.crm.service.exception.NoEnoughIntegralException;
import com.ea.card.crm.service.exception.NoneHistoryRegisterException;
import com.ea.card.crm.service.exception.NoneRegisterException;
import com.ea.card.crm.service.strategy.IntegralLotteryStrategy;
import com.ea.card.crm.service.strategy.LotterySuccessStrategy;
import com.ea.card.crm.service.strategy.impl.LotterySuccessIntegralStrategy;
import com.ea.card.crm.service.vo.UCMemberExtData;
import com.ea.card.crm.service.vo.UCMemberExtResult;
import com.ea.card.crm.service.vo.WechatMessageResponse;
import com.lmtech.common.ExeResult;
import com.lmtech.common.PageData;
import com.lmtech.common.StateResult;
import com.lmtech.dao.Dao;
import com.lmtech.service.support.AbstractDbManagerBaseImpl;
import com.lmtech.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.util.*;

@Service
@RefreshScope
public class IntegralServiceImpl extends AbstractDbManagerBaseImpl<IntegralSignLog> implements IntegralService {

    @Autowired
    private IntegralSignLogDao integralSignLogDao;
    @Autowired
    private IntegralSetDao integralSetDao;
    @Autowired
    private IntegralLotteryStrategy lotteryStrategy;
    @Autowired
    private LotteryProductService lotteryProductService;
    @Autowired
    private IntegralLotteryService integralLotteryService;
    @Autowired
    private IntegralLogService integralLogService;

    @Override
    public Dao getDao() {
        return integralSignLogDao;
    }

    @Value("${service.send_wechat_message}")
    public String SEND_WECHAT_MESSAGE;

    @Value("${service.update_token}")
    public String UPDATE_TOKEN;
    @Value("${wx.template_id}")
    public String TEMPLATE_ID;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MemberService memberService;
    @Autowired
    private WxService wxService;
    @Autowired
    private MemberRegisterService memberRegisterService;

    /**
     * 积分获取接口
     */
    @Override
    public GetIntegralResult getIntegral(String userId) {
        MemberRegister memberRegister = memberRegisterService.getByUserId(userId);
        if (memberRegister == null) {
            throw new NoneRegisterException();
        }

        GetIntegralResult result = new GetIntegralResult();
        GetIntegralData getIntegralData = new GetIntegralData();
        getIntegralData.setSumIntegralNumber(memberRegister.getIntegral());
        getIntegralData = newGetIntegralData(userId, getIntegralData);

        result.setData(getIntegralData);
        return result;
    }

    @Override
    public GetIntegralResult getHistoryIntegral(String userId) {
        MemberRegister historyRegister = memberRegisterService.getByUserIdAndIsDelete(userId, MemberRegister.DELETE_YES);
        if (historyRegister == null) {
            throw new NoneHistoryRegisterException();
        }

        GetIntegralResult result = new GetIntegralResult();
        GetIntegralData getIntegralData = new GetIntegralData();
        getIntegralData.setSumIntegralNumber(historyRegister.getIntegral());
        getIntegralData = newGetIntegralData(userId, getIntegralData);

        result.setData(getIntegralData);
        return result;
    }

    /**
     * 积分页获取当日，当日是否签到，连续签到值
     *
     * @param rmap
     * @return
     */
    @Override
    public Map<String, Integer> getTodayData(Map<String, Object> rmap) {
        Map<String, Integer> returnMap = new HashMap<String, Integer>();

        int signCount;//连续签到天数
        int isSign;//是否已签到
        if (rmap != null) {//不是第一次签到，之前签过
            signCount = (int) rmap.get("signCount");
            isSign = (int) rmap.get("isSign");
        } else {//第一次签到
            signCount = IntegralConstants.ZERO;
            isSign = IntegralConstants.ZERO;
        }

        int _day;
        if (isSign == IntegralConstants.ONE) {//当日已签
            _day = signCount * IntegralConstants.ONE;
        } else {//当日未签
            _day = signCount + IntegralConstants.ONE;
        }

        //if(_day >= IntegralConstants.FOUR){_day = IntegralConstants.FOUR;}//连续签到超过3天,及第4天后，每天都是20积分

        BigInteger b_signCount = new BigInteger(String.valueOf(_day));
        BigInteger b_mod = new BigInteger(String.valueOf(IntegralConstants.SEVEN));//7天为一个签到积分周期

        int modDay = b_signCount.mod(b_mod).intValue();//求余,当日

        returnMap.put("isSign", isSign);
        returnMap.put("signCount", signCount);
        returnMap.put("modDay", modDay);
        //returnMap.put("modDay",_day);
        return returnMap;
    }

    /**
     * 获取积分页 当日和次日积分值
     *
     * @param userId
     * @param old_getIntegralData
     * @return
     */
    @Override
    public GetIntegralData newGetIntegralData(String userId, GetIntegralData old_getIntegralData) {

        GetIntegralData getIntegralData = old_getIntegralData;
        Map<String, Integer> returnMap = this.getTodayData(integralSignLogDao.getIntegralSignLog(userId, IntegralConstants.SOURCE_TWO));

        getIntegralData.setIsSign(returnMap.get("isSign"));
        getIntegralData.setSignCount(returnMap.get("signCount"));

        int modDay = returnMap.get("modDay");//求余后的今日
        int nextDay = modDay + IntegralConstants.ONE;//次日

        if (modDay == IntegralConstants.ZERO) {
            modDay = IntegralConstants.SEVEN;
        }

        List<IntegralSet> type_list = this.getIntegralSetAll(IntegralConstants.SOURCE_TWO);

        for (int i = 0; i < type_list.size(); i++) {
            if (type_list.get(i).getDayNo() == modDay) {//当日与当日积分
                getIntegralData.setToDayNo(type_list.get(i).getDayNo());
                getIntegralData.setToDaybonus(type_list.get(i).getBonus());
            }
            if (type_list.get(i).getDayNo() == nextDay) {//次日与次日积分
                getIntegralData.setNextDayNo(type_list.get(i).getDayNo());
                getIntegralData.setNextDaybonus(type_list.get(i).getBonus());
            }
        }
        return getIntegralData;
    }

    @Override
    public void existOfUserId(String userId, int integralSource) {
        LoggerManager.info("是否存在该类型用户日志服务被调用");
        if (integralSource == IntegralConstants.SOURCE_TWO) {
            this.existOfSign(userId, integralSource);
        } else if (integralSource == IntegralConstants.SOURCE_THERE) {
            this.existOfShare(userId, integralSource);
        } else {
            this.existOfSendCard(userId, integralSource);
        }
    }

    /**
     * 公共属性
     *
     * @param userId
     * @param integralSource
     * @return
     */
    public IntegralSignLog commonProperty(String userId, int integralSource) {
        IntegralSignLog integralSignLog = new IntegralSignLog();
        integralSignLog.setUserId(userId);
        integralSignLog.setCreateUser(userId);
        integralSignLog.setCreateUserName(userId);
        integralSignLog.setUpdateUser(userId);
        integralSignLog.setUpdateUserName(userId);
        integralSignLog.setBonusType(integralSource);
        return integralSignLog;
    }

    /**
     * 是否存在该用户的签到日志
     *
     * @param userId
     * @param integralSource
     */
    public void existOfSign(String userId, int integralSource) {
        LoggerManager.info("是否存在该类型用户日志服务被调用");
        boolean exist = integralSignLogDao.existOfUserId(userId, integralSource);
        if (!exist) {
            IntegralSignLog integralSignLog = this.commonProperty(userId, integralSource);
            integralSignLog.setSignCount(IntegralConstants.ONE);
            integralSignLog.setIsSign(IntegralConstants.ONE);
            integralSignLog.setDayCount(IntegralConstants.ONE);
            this.add(integralSignLog);
        }
    }

    /**
     * 是否存在该用户的赠卡日志
     *
     * @param userId
     * @param integralSource
     */
    public void existOfSendCard(String userId, int integralSource) {
        LoggerManager.info("是否存在该类型用户日志服务被调用");
        boolean exist = integralSignLogDao.existOfUserId(userId, integralSource);
        if (!exist) {
            IntegralSignLog integralSignLog = this.commonProperty(userId, integralSource);
            integralSignLog.setDayCount(IntegralConstants.ONE);
            this.add(integralSignLog);
        }
    }

    /**
     * 是否存在该用户的分享日志
     *
     * @param userId
     * @param integralSource
     */
    public void existOfShare(String userId, int integralSource) {
        LoggerManager.info("是否存在该类型用户日志服务被调用");
        boolean exist = integralSignLogDao.existOfUserId(userId, integralSource);
        if (!exist) {
            IntegralSignLog integralSignLog = this.commonProperty(userId, integralSource);
            integralSignLog.setDayCount(IntegralConstants.ONE);
            this.add(integralSignLog);
        }
    }

    public StateResult integralSignMethod(IntegralDO integralDO, int integralNumber) {
        ExeResult exeResult = new ExeResult();
        integralChange(integralDO.getUserId(), integralDO.getIntegralType(), integralDO.getIntegralSource(), integralDO.getIntegralOrderId(), integralNumber);
        exeResult.setSuccess(true);
        exeResult.setMessage("积分签到成功");
        return exeResult.getResult();
    }

    /**
     * 签到类型
     *
     * @return
     */
    public StateResult sign(IntegralDO integralDO) {
        LoggerManager.info("积分签到服务被调用");
        int integralNumber;
        List<IntegralSet> list = this.getIntegralSetAll(integralDO.getIntegralSource());
        Map<String, Object> signLog = integralSignLogDao.getIntegralSignLog(integralDO.getUserId(), integralDO.getIntegralSource());

        if (signLog == null) {
            integralNumber = list.get(IntegralConstants.ZERO).getBonus();//获取签到第一天
            this.existOfSign(integralDO.getUserId(), integralDO.getIntegralSource());//新增日志
            StateResult response = this.integralSignMethod(integralDO, integralNumber);
            if (!response.isSuccess()) {
                LoggerManager.error("积分签到失败。" + response.getMsg());
                response = new StateResult();
                response.setState(IntegralConstants._ONE);
                response.setMsg(IntegralConstants.ERROR1);
            }
            //response.setData(list);
            return response;//第一次签到，直接返回
        } else {
            int signCount = (int) signLog.get("signCount");//连续签到的天数

            //if(signCount >= IntegralConstants.THERE){signCount = IntegralConstants.THERE;}//连续签到超过3天,及第4天后，每天都是20积分

            BigInteger b_signCount = new BigInteger(String.valueOf(signCount));
            BigInteger b_mod = new BigInteger(String.valueOf(IntegralConstants.SEVEN));//7天为一个签到积分周期
            int modDay = b_signCount.mod(b_mod).intValue();//求余

            integralNumber = list.get(modDay).getBonus();//今天的积分
            // integralNumber = list.get(signCount).getBonus();//今天的积分
        }

        int isSign = (int) signLog.get("isSign");//是否签到
        if (isSign == IntegralConstants.ONE) {//当日已签到，不能再签了
            StateResult response = new StateResult();
            response.setState(IntegralConstants.ZERO);
            response.setMsg(IntegralConstants.SIGNED);
            //response.setData(list);//签到需要返回积分列表
            return response;
        } else {
            StateResult response = this.integralSignMethod(integralDO, integralNumber);
            if (response.isSuccess()) {
                if (signLog != null) {
                    int signCount = (int) signLog.get("signCount") + IntegralConstants.ONE;//连续签到的天数

                    integralDO.setSignCount(signCount);
                    integralDO.setDayCount(IntegralConstants.ONE);
                    integralDO.setIsSign(IntegralConstants.ONE);

                    boolean flag = integralSignLogDao.updateIntegralSignLog(integralDO) > IntegralConstants.ZERO;
                    LoggerManager.info("修改该类型用户日志服务被调用,flag == " + flag);
                }
            } else {
                LoggerManager.error("积分签到失败。" + response.getMsg());
                response = new StateResult();
                response.setState(IntegralConstants._ONE);
                response.setMsg(IntegralConstants.ERROR1);
            }
            //response.setData(list);//签到需要返回积分列表
            return response;
        }
    }

    /**
     * 分享类型
     *
     * @return
     */
    public StateResult share(IntegralDO integralDO) {
        LoggerManager.info("积分分享服务被调用");
        List<IntegralSet> list = this.getIntegralSetAll(integralDO.getIntegralSource());
        Map<String, Object> signLog = integralSignLogDao.getIntegralSignLog(integralDO.getUserId(), integralDO.getIntegralSource());
        int integralNumber = list.get(IntegralConstants.ZERO).getBonus();//获取分享积分;
        if (signLog == null) {
            this.existOfShare(integralDO.getUserId(), integralDO.getIntegralSource());//新增日志
            StateResult response = this.integralSignMethod(integralDO, integralNumber);
            if (!response.isSuccess()) {
                LoggerManager.error("分享失败。" + response.getMsg());
                response = new StateResult();
                response.setState(IntegralConstants._ONE);
                response.setMsg(IntegralConstants.ERROR1);
            }
            return response;
        } else {
            return this.commonResponse(signLog, integralDO, integralNumber);
        }
    }

    public StateResult commonResponse(Map<String, Object> signLog, IntegralDO integralDO, int integralNumber) {
        StateResult response = new StateResult();
        int dayCount = (int) signLog.get("dayCount");//每日分享,赠卡次数
        int isSign;//当日分享，赠卡是否完成，1完成
        List<BonusType> type = this.groupbytype();//积分类型和每日最大次数的分组
        for (int i = 0; i < type.size(); i++) {
            if (type.get(i).getBonusType() == integralDO.getIntegralSource()) {
                int dayMax = type.get(i).getDayMax();//日最大次数
                if (dayMax > dayCount) {
                    response = this.integralSignMethod(integralDO, integralNumber);
                    isSign = IntegralConstants.ZERO;
                } else {
                    isSign = IntegralConstants.ONE;
                }

                dayCount = dayCount + IntegralConstants.ONE;
                integralDO.setDayCount(dayCount);

                if (dayMax == dayCount) {
                    isSign = IntegralConstants.ONE;
                }
                integralDO.setIsSign(isSign);
                boolean flag = integralSignLogDao.updateIntegralSignLog(integralDO) > IntegralConstants.ZERO;
                LoggerManager.info("修改该类型用户日志服务被调用,flag == " + flag);
            }
        }
        response.setMsg(IntegralConstants.SUCCESS);
        return response;
    }

    /**
     * 赠卡类型
     *
     * @return
     */
    public StateResult sendCard(IntegralDO integralDO) {
        LoggerManager.info("积分赠卡服务被调用");
        List<IntegralSet> list = this.getIntegralSetAll(integralDO.getIntegralSource());
        Map<String, Object> signLog = integralSignLogDao.getIntegralSignLog(integralDO.getUserId(), integralDO.getIntegralSource());
        int integralNumber = list.get(IntegralConstants.ZERO).getBonus();//获取赠卡积分;
        if (signLog == null) {
            this.existOfSendCard(integralDO.getUserId(), integralDO.getIntegralSource());//新增日志
            StateResult response = this.integralSignMethod(integralDO, integralNumber);
            if (!response.isSuccess()) {
                LoggerManager.error("赠卡失败。" + response.getMsg());
                response = new StateResult();
                response.setState(IntegralConstants._ONE);
                response.setMsg(IntegralConstants.ERROR1);
            }
            return response;
        } else {
            return this.commonResponse(signLog, integralDO, integralNumber);
        }
    }

    /**
     * 积分签到接口
     */
    @Override
    public StateResult integralSign(IntegralDO integralDO) {
        LoggerManager.info("积分服务被调用");
        if (integralDO.getIntegralSource() == IntegralConstants.SOURCE_TWO) {//签到
            return this.sign(integralDO);
        } else if (integralDO.getIntegralSource() == IntegralConstants.SOURCE_THERE) {//分享
            return this.share(integralDO);
        } else {//赠卡,1
            //return this.sendCard(integralDO);
            throw new IllegalArgumentException(IntegralConstants.ERROR_PARAMETER);
        }
    }

    /**
     * 积分明细接口
     */
    @Override
    public IntegralDetailsResult integralDetails(String userId, int integralType, int pageNum,
                                                 int pageSize, String t_id) {
        IntegralLog param = new IntegralLog();
        param.setUserId(userId);
        param.setType(integralType);

        PageData<IntegralLog> pageData = integralLogService.getDataListOfPage(param, pageNum, pageSize);
        IntegralDetailsResult response = new IntegralDetailsResult();
        response.setState(0);
        IntegralDetailsData detailsData = new IntegralDetailsData();
        detailsData.setTotal(pageData.getTotal());
        detailsData.setTotalPage(pageData.getTotalPage());
        detailsData.setLastPage(pageData.getTotalPage());
        List<IntegralMemberDetailSchema> detailSchemas = new ArrayList<>();
        for (IntegralLog item : pageData.getItems()) {
            IntegralMemberDetailSchema detailSchema = new IntegralMemberDetailSchema();
            detailSchema.setCreateTime(DateUtil.format(item.getCreateDate()));
            detailSchema.setIntegralNumber(item.getIntegral());
            detailSchema.setIntegralOrderId(item.getOrderId());
            detailSchema.setIntegralSource(item.getSource());
            detailSchema.setUserId(item.getUserId());
            detailSchemas.add(detailSchema);
        }
        detailsData.setList(detailSchemas);
        response.setData(detailsData);
        return response;
    }

    @Override
    public Map<String, Object> getIntegralSignLog(String userId, int integralSource) {
        return integralSignLogDao.getIntegralSignLog(userId, integralSource);
    }

    @Override
    public Integer getIntegralSet(int dayNo, int integralSource) {
        return integralSetDao.getIntegralSet(dayNo, integralSource);
    }

    @Override
    public List<IntegralSet> getIntegralSetAll(int integralSource) {
        return integralSetDao.getIntegralSetAll(integralSource);
    }

    @Override
    public void noticeIntegralUpdate(String userId) {
        UCMemberExtResult result = memberService.getUcMemberExt(userId);
        if (result.isSuccess()) {
            MemberRegister register = memberRegisterService.getByUserId(userId);
            if (result.getData() == null) {
                UCMemberExtData uc = new UCMemberExtData();
                uc.setUserId(userId);
                uc.setIntegralMemberLevel(IntegralConstants.ONE);
                uc.setSumIntegralNumber(IntegralConstants.L_ZERO);
                result.setData(uc);
            }
            long integralNumber = result.getData().getSumIntegralNumber();
            wxService.updateCardBonus(register.getCardId(), register.getCode(), integralNumber, null, false);
        } else {
            LoggerManager.warn("更新用户：" + userId + "积分数据到微信失败。用户中心数据查询出错：" + result.getMsg());
        }
    }

    @Override
    public void increaseIntegral(String userId, long integral, int source) {
        integralChange(userId, IntegralConstants.ONE, source, IdWorkerUtil.generateStringId(), (int) integral);
        noticeIntegralUpdate(userId);
    }

    @Override
    public void consumeIntegral(String userId, long integral, int source) {
        integralChange(userId, IntegralConstants.TWO, source, IdWorkerUtil.generateStringId(), (int) integral);
        noticeIntegralUpdate(userId);
    }

    @Override
    public Map<String, Object> integralLottery(String userId) {
        List<LotteryProduct> products = lotteryProductService.getProducts();
        LotteryProduct product = lotteryStrategy.drawLottery(products);
        //抽奖消费积分
        integralChange(userId, IntegralConstants.TWO, IntegralConstants.SOURCE_FOUR, IdWorkerUtil.generateStringId(), integralLotteryService.getIntegralPay());

        IntegralLottery integralLottery = new IntegralLottery();
        integralLottery.setUserId(userId);
        integralLottery.setPrize(product.isPrize());
        integralLottery.setResultId(product.getId());
        integralLottery.setProductIds(this.getProductIds(products));
        integralLottery.setStrategy(lotteryStrategy.getClass().getSimpleName());
        integralLotteryService.add(integralLottery);

        //更新微信积分数据
        noticeIntegralUpdate(userId);

        //中奖成功处理
        if (product.isPrize()) {
            if (LotteryProduct.TYPE_INTEGRAL == product.getType()) {
                LotterySuccessStrategy strategy = SpringUtil.getObjectT(LotterySuccessIntegralStrategy.class);
                strategy.handle(userId, product);
            }
        }

        Map<String, Object> data = new HashMap<>();
        data.put("productId", product.getId());
        data.put("type", product.getType());
        data.put("name", product.getName());
        return data;
    }

    @Override
    public List<BonusType> groupbytype() {
        return integralSetDao.groupbytype();
    }

    @Override
    public List<IntegralSignLog> getIntegralSignLogList(String userId, int integralSource) {
        return integralSignLogDao.getIntegralSignLogList(userId, integralSource);
    }

    private String getProductIds(List<LotteryProduct> products) {
        StringBuilder sb = new StringBuilder();
        int i = IntegralConstants.ZERO;
        for (LotteryProduct product : products) {
            if (i > IntegralConstants.ZERO) {
                sb.append(",");
            }
            sb.append(product.getId());
            i++;
        }
        return sb.toString();
    }

    @Override
    public void sendIntegralMsgToWx(String openId, String msgContent) {
        updateToken();
//		StringBuffer sb = new StringBuffer();
//		sb.append("{'first':{'value':'111111'}},");
//		sb.append("{'orderID':{'value':'222222'},");
//		sb.append("{'orderMoneySum':{'value':'33333333'}},");
//		sb.append("{'backupFieldName':{'value':'44444444'}},");
//		sb.append("{'backupFieldData':{'value':'555555'}},");
//		sb.append("{'remark':{'value':'666666666'}}");
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("tId", IdWorkerUtil.generateStringId());
        map.add("sys", "38");
        map.add("userType", IntegralConstants.ONE);
        map.add("templateId", TEMPLATE_ID);
        map.add("msgContent", msgContent);
        map.add("openId", openId);
        map.add("url", "");
        map.add("weChatType", IntegralConstants.TWO);
        LoggerManager.info("TEMPLATE_ID------------------" + TEMPLATE_ID);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, null);
        WechatMessageResponse wechatMessageResponse = restTemplate.postForObject(SEND_WECHAT_MESSAGE, request, WechatMessageResponse.class);
        LoggerManager.debug("wechatMessageResponse:" + JsonUtil.toJson(wechatMessageResponse));
        if (!("0".equals(wechatMessageResponse.getState()))) {
            throw new IntegralConsumeException(wechatMessageResponse.getMsg(), -1);
        }
    }

    @Override
    public WechatMessageResponse updateToken() {
        MultiValueMap<String, Object> maps = new LinkedMultiValueMap<>();

        maps.add("tId", IdWorkerUtil.generateStringId());
        maps.add("sys", "38");
        maps.add("weChatType", IntegralConstants.TWO);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(maps, null);
        WechatMessageResponse wechatMessageResponse = restTemplate.postForObject(UPDATE_TOKEN, request, WechatMessageResponse.class);
        LoggerManager.debug("wechatMessageResponse:" + JsonUtil.toJson(wechatMessageResponse));
        return wechatMessageResponse;
    }

    /**
     * 赠卡次数回写接口
     *
     * @return
     */
    @Override
    public boolean sendCardCallBack(String userId) {
        LoggerManager.info("赠卡次数回写服务被调用");
        Map<String, Object> signLog = integralSignLogDao.getIntegralSignLog(userId, IntegralConstants.SOURCE_ONE);
        if (signLog == null) {
            this.existOfSendCard(userId, IntegralConstants.SOURCE_ONE);//新增日志
            return true;
        } else {
            return this.sendCardCallBackResponse(signLog, userId);
        }
    }

    public boolean sendCardCallBackResponse(Map<String, Object> signLog, String userId) {
        LoggerManager.info("赠卡次数回写服务被调用");
        boolean signed = false;
        IntegralDO integralDO = new IntegralDO();
        integralDO.setUserId(userId);
        integralDO.setIntegralSource(IntegralConstants.SOURCE_ONE);
        integralDO.setUpdateTime(new Date());

        int dayCount = (int) signLog.get("dayCount");//每日分享,赠卡次数
        int isSign;//当日分享，赠卡是否完成，1完成,0未完成
        List<BonusType> type = this.groupbytype();//积分类型和每日最大次数的分组
        for (int i = 0; i < type.size(); i++) {
            if (type.get(i).getBonusType() == integralDO.getIntegralSource()) {
                int dayMax = type.get(i).getDayMax();//日最大次数
                if (dayMax > dayCount) {
                    isSign = IntegralConstants.ZERO;
                    signed = true;
                } else {
                    isSign = IntegralConstants.ONE;
                    signed = false;
                }

                dayCount = dayCount + IntegralConstants.ONE;
                integralDO.setDayCount(dayCount);

                if (dayMax == dayCount) {
                    isSign = IntegralConstants.ONE;
                    signed = false;
                }
                integralDO.setIsSign(isSign);
                boolean flag = integralSignLogDao.updateIntegralSignLog(integralDO) > IntegralConstants.ZERO;
                LoggerManager.info("修改该类型用户日志服务被调用,flag == " + flag);
            }
        }
        return signed;
    }

    private void integralChange(String userId, int integralType, int integralSource, String integralOrderId, int integralNumber) {
        if (integralType == IntegralConstants.TYPE_TWO) {
            //消费积分，需先判断用户积分是否足够
            GetIntegralResult result = getIntegral(userId);
            if (result.isSuccess()) {
                long integral = result.getData().getSumIntegralNumber();
                if (integral < integralNumber) {
                    throw new NoEnoughIntegralException();
                }
            } else {
                throw new RuntimeException("获取用户积分出错。");
            }
        }

        IntegralLog integralLog = new IntegralLog();
        integralLog.setUserId(userId);
        integralLog.setType(integralType);
        integralLog.setSource(integralSource);
        integralLog.setOrderId(integralOrderId);
        integralLog.setIntegral(integralNumber);
        integralLogService.add(integralLog);
    }
}
