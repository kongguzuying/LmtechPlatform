package com.ea.card.crm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ea.card.crm.constants.ErrorConstants;
import com.ea.card.crm.facade.request.ActiveMemberCardRequest;
import com.ea.card.crm.facade.request.IncreaseLevelRequest;
import com.ea.card.crm.facade.response.GetSmsCodeResult;
import com.ea.card.crm.model.CardActiveRecord;
import com.ea.card.crm.model.GiftMemberCard;
import com.ea.card.crm.model.MemberRegister;
import com.ea.card.crm.service.*;
import com.ea.card.crm.service.constants.WxConstants;
import com.ea.card.crm.service.exception.ActiveMemberCardException;
import com.ea.card.crm.service.exception.NotExistCardActiveRecord;
import com.ea.card.crm.service.exception.RechargePayException;
import com.ea.card.crm.service.exception.WxIntfInvokeException;
import com.ea.card.crm.service.msghandler.OutStrParam;
import com.ea.card.crm.service.msghandler.ReceiveCardMessageHandler;
import com.ea.card.crm.service.vo.*;
import com.lmtech.common.ExeResult;
import com.lmtech.common.StateResult;
import com.lmtech.exceptions.ErrorCodeException;
import com.lmtech.util.*;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.util.HashMap;
import java.util.Map;

@Service
@RefreshScope
public class MemberCardServiceImpl implements MemberCardService {

	@Value("${service.url_uc_register}")
    public String URL_UC_REGISTER;

	@Value("${service.url_uc_finduser}")
    public String URL_UC_FINDUSER;

	@Value("${service.url_account_checkuser}")
    public String URL_ACCOUNT_CHECKUSER;

	@Value("${service.url_account_register}")
    public String URL_ACCOUNT_REGISTER;

	@Value("${service.url_account_mybalance}")
    public String URL_ACCOUNT_MYBALANCE;

	@Value("${service.url_oa_exist_user}")
    public String URL_OA_EXIST_USER;

	@Value("${service.url_login4wap}")
    public String URL_LOGIN4WAP;

	@Autowired
    private WxService wxService;
    @Autowired
    private CardActiveRecordService cardActiveRecordService;
    @Autowired
    private MemberRegisterService memberRegisterService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private GiftMemberCardService giftMemberCardService;
    @Autowired
    private ReceiveCardMessageHandler receiveCardMessageHandler;
    @Autowired
    private AliyunMessageService aliyunMessageService;

    @Override
    public StateResult userAuth(String code) {
        if (StringUtil.isNullOrEmpty(code)) {
            throw new IllegalArgumentException("用户授权Code为空");
        }

        PageAuthResponse authResponse = wxService.getPageAuthInfo(code);
        WxUserInfo userInfo = wxService.getUserInfo(authResponse.getAccess_token(), authResponse.getOpenid());

        boolean register;
        MemberRegister memberRegister = memberRegisterService.getByOpenIdAndIsDelete(userInfo.getUnionid(), MemberRegister.DELETE_NO);
        if (memberRegister == null) {
            CardActiveRecord activeRecord = new CardActiveRecord();
            activeRecord.setOpenId(userInfo.getOpenid());
            activeRecord.setUniqueId(userInfo.getUnionid());
            activeRecord.setHeadimgurl(userInfo.getHeadimgurl());
            activeRecord.setNickname(userInfo.getNickname());
            activeRecord.setSex(userInfo.getSex());
            activeRecord.setStatus(CardActiveRecord.STATUS_APPLYING);
            activeRecord.setRefreshToken(authResponse.getRefresh_token());
            cardActiveRecordService.add(activeRecord);
            register = false;
        } else {
            register = true;
        }

        ExeResult result = new ExeResult();
        result.setSuccess(true);
        Map<String, Object> data = new HashMap<>();
        data.put("user", userInfo);
        data.put("register", register);
        result.setData(data);
        return result.getResult();
    }

    @Override
    public StateResult appletUserAuth(String code, String encryptedData, String iv)
    		throws InvalidAlgorithmParameterException, UnsupportedEncodingException {

        if (StringUtil.isNullOrEmpty(code)) {
            throw new IllegalArgumentException("用户授权Code为空");
        }
        if (StringUtil.isNullOrEmpty(encryptedData)) {
            throw new IllegalArgumentException("用户敏感加密数据不能为空!");
        }
        if (StringUtil.isNullOrEmpty(iv)) {
            throw new IllegalArgumentException("偏移量不能为空!");
        }

        PageAuthResponse authResponse = wxService.getAppletPageAuthInfo(code);
        String sessionKey = authResponse.getSession_key();
        if (StringUtil.isNullOrEmpty(sessionKey)) {
            throw new IllegalArgumentException("微信接口异常，获取sessionKey失败！");
        }
        WxUserInfo userInfo = new WxUserInfo();
        AESUtil aes = new AESUtil();
        byte[] resultByte = aes.decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(sessionKey), Base64.decodeBase64(iv));
        if(null != resultByte && resultByte.length > 0){
			String userInfoStr = new String(resultByte, "UTF-8");
			JSONObject obj = JSONObject.parseObject(userInfoStr);
            userInfo.setOpenid(obj.getString("openId"));
            userInfo.setNickname(obj.getString("nickName"));
            userInfo.setSex(obj.getInteger("gender"));
            userInfo.setProvince(obj.getString("province"));
            userInfo.setCity(obj.getString("city"));
            userInfo.setCountry(obj.getString("country"));
            userInfo.setHeadimgurl(obj.getString("avatarUrl"));
            userInfo.setUnionid(obj.getString("unionId"));
        }
        boolean register;
        String token = null, phone = null;
        MemberRegister memberRegister = memberRegisterService.getByOpenId(userInfo.getUnionid());
        if (memberRegister == null) {
            CardActiveRecord activeRecord = new CardActiveRecord();
            activeRecord.setOpenId(userInfo.getOpenid());
            activeRecord.setUniqueId(userInfo.getUnionid());
            activeRecord.setHeadimgurl(userInfo.getHeadimgurl());
            activeRecord.setNickname(userInfo.getNickname());
            activeRecord.setStatus(CardActiveRecord.STATUS_APPLYING);
            activeRecord.setSex(userInfo.getSex());
            activeRecord.setRefreshToken(authResponse.getRefresh_token());
            cardActiveRecordService.add(activeRecord);
            register = false;
        } else {
            register = true;
            /*phone = memberRegister.getPhone();
            if (!StringUtil.isNullOrEmpty(memberRegister.getAuthRefreshToken())) {
                //登录门户获取token
                LoginWapResult loginWapResult = memberService.reLoginWap(memberRegister);
                if (loginWapResult.isSuccess()) {
                    token = loginWapResult.getData().getToken();
                } else {
                    LoggerManager.error("刷新登录门户失败，userId:" + memberRegister.getUserId() + ",errMsg:" + loginWapResult.getMsg());
                }
            }*/
        }
        ExeResult result = new ExeResult();
        result.setSuccess(true);
        Map<String, Object> data = new HashMap<>();
        data.put("user", userInfo);
        data.put("register", register);
        data.put("token", token);
        data.put("phone", phone);
        result.setData(data);
        return result.getResult();
    }

    @Override
    @Transactional
    public void activeMemberCardInTrans(ActiveMemberCardRequest request, ExeResult result) {
        GiftMemberCard giftMemberCard = null;
        if (request.getActionType() == ActiveMemberCardRequest.ACTION_TYPE_PAYCARD || request.getActionType() == ActiveMemberCardRequest.ACTION_TYPE_GIFTPRESENT) {
            //购买赠送、大礼包赠送
            if (StringUtil.isNullOrEmpty(request.getPresentRecordId())) {
                throw new IllegalArgumentException("传入presentRecordId为空");
            }
            if (StringUtil.isNullOrEmpty(request.getOwnerUnionId())) {
                throw new IllegalArgumentException("传入ownerUnionId为空");
            }
            //是否存在已激活的会员卡
            MemberRegister memberRegister = memberRegisterService.getByOpenIdAndIsDelete(request.getOpenId(), MemberRegister.DELETE_NO);
            if(memberRegister!=null) {
            	throw new IllegalArgumentException("会员卡已激活过，请勿重复激活");
            }
            //判断多人领卡时，是否能够领取
            giftMemberCard = giftMemberCardService.receiveCardFromPresent(request.getPresentRecordId(), request.getOpenId());
            giftMemberCard.setRecvOpenId(request.getOpenId());
            giftMemberCard.setStatus(GiftMemberCard.STATUS_RECVLOCK);
            giftMemberCard.setPhone(request.getPhone());
            giftMemberCardService.edit(giftMemberCard);

            //激活
            try {
                activeMemberCard(request, giftMemberCard.getId());
            } catch (Exception e) {
                //还原礼品卡状态
                giftMemberCardService.receiveCardFromPresentCallBack(request.getPresentRecordId(), giftMemberCard);
                throw e;
            }
        } else if (request.getActionType() == ActiveMemberCardRequest.ACTION_TYPE_OFFICIAL) {
            //公众号领卡激活
            if (StringUtil.isNullOrEmpty(request.getOpenId())) {
                throw new IllegalArgumentException("openid不允许为空");
            }
            if (StringUtil.isNullOrEmpty(request.getCode())) {
                throw new IllegalArgumentException("卡片code不允许为空");
            }

            WxDecryptCodeResponse decryptCodeResponse = wxService.decryptCode(request.getCode());
            if (decryptCodeResponse.isSuccess()) {
                request.setCode(decryptCodeResponse.getCode());
            } else {
                throw new WxIntfInvokeException(decryptCodeResponse);
            }
            WxUserInfo userInfo = wxService.getUserInfoOfOfficial(request.getOpenId());
            MemberRegister memberRegister = memberRegisterService.getByOpenIdAndIsDelete(userInfo.getUnionid(), MemberRegister.DELETE_NO);
            if (memberRegister == null) {
                //未激活过的用户，执行激活流程
                //将openid设置为unionid
                request.setOpenId(userInfo.getUnionid());
                //添加激活记录
                CardActiveRecord activeRecord = new CardActiveRecord();
                activeRecord.setOpenId(userInfo.getOpenid());
                activeRecord.setUniqueId(userInfo.getUnionid());
                activeRecord.setHeadimgurl(userInfo.getHeadimgurl());
                activeRecord.setNickname(userInfo.getNickname());
                activeRecord.setSex(userInfo.getSex());
                activeRecord.setStatus(CardActiveRecord.STATUS_APPLYING);
                activeRecord.setCardId(WxConstants.WX_CARD_ID);
                activeRecord.setCode(request.getCode());
                activeRecord.setCardLevel(MemberRegister.MLEVEL_NORMAL);
                cardActiveRecordService.add(activeRecord);

                activeMemberCard(request, null);

                //手动调用微信回调
                MemberRegister deleteMemberRegister = memberRegisterService.getByOpenIdAndIsDelete(userInfo.getUnionid(), MemberRegister.DELETE_YES);
                WxReceiveCardMessage message = new WxReceiveCardMessage();
                message.setCardId(WxConstants.WX_CARD_ID);
                message.setUserCardCode(request.getCode());
                message.setFromUserName(activeRecord.getOpenId());
                message.setRestoreMemberCard(deleteMemberRegister != null ? 1 : 0);//是否首次领卡
                OutStrParam outStrParam = new OutStrParam();
                outStrParam.setActionType(ActiveMemberCardRequest.ACTION_TYPE_SHARECARD);
                message.setOuterStr(UrlUtil.encodeUrl(JsonUtil.toJson(outStrParam)));
                receiveCardMessageHandler.handle(message);
            } else {
                //已激活
                LoggerManager.info("用户已激活，无需领卡激活。");
            }
        } else {
            //普通激活
            activeMemberCard(request, null);
        }

        Map<String, String> cardSign = wxService.getCardApiSign(WxConstants.WX_CARD_ID, null, null);
        cardSign.put("giftCardId", (giftMemberCard != null ? giftMemberCard.getId() : ""));
        cardSign.put("cardId", WxConstants.WX_CARD_ID);
        result.setData(cardSign);
    }

    @Override
    public boolean increaseLevelParamCheck(IncreaseLevelRequest request) {

        //TODO 天数根据数据库中查找 卡等级从数据库中查找
        if ((request.getPresentDays() != 30 && request.getPresentDays() !=0 ) || (request.getTargetLevel() != MemberRegister.MLEVEL_NORMAL && request.getTargetLevel() != MemberRegister.MLEVEL_VPASS)) {
            throw new RechargePayException(ErrorConstants.ERR_CRM_ORDER_PAY_PARAMETER_MSG, ErrorConstants.ERR_CRM_ORDER_PAY_PARAMETER);
        }
        return true;
    }

    private void activeMemberCard(ActiveMemberCardRequest request, String giftCardId) {
        CardActiveRecord record = cardActiveRecordService.getApplyingRecordByUniqueId(request.getOpenId());
        try {
            if (record == null) {
                throw new NotExistCardActiveRecord();
            }
            record.setPhone(request.getPhone());
            record.setFriendOpenId(null);
            record.setAppType((WxConstants.isWxApplet() ? MemberRegister.APP_TYPE_APPLET : MemberRegister.APP_TYPE_H5));
            OutStrParam outstr = new OutStrParam();
            outstr.setActionType(request.getActionType());
            outstr.setOwnerOpenId(request.getOwnerUnionId());
            if (WxConstants.isWxApplet()) {
                outstr.setAppletOpenId(request.getOpenId());
            }
            outstr.setGiftCardId(giftCardId);
            record.setOutstr(JsonUtil.toJson(outstr));
            record.setUserId(IdWorkerUtil.generateStringId());
            record.setBalance(0);
            record.setUserType("1");

            //校验验证码
            ExeResult smsCheckResult = aliyunMessageService.checkSmsValidCode(request.getVerifyKey(), request.getVerifyCode());
            if (!smsCheckResult.isSuccess()) {
                throw new RuntimeException(smsCheckResult.getMessage());
            }

            /*
            String tid = IdWorkerUtil.generateStringId();
            LoggerManager.info("校验用户中心数据 => 开始");
            String userId = registerAndBindUser(tid, request.getVerifyCode(), request.getVerifyKey(), request.isIgnoreVerifyCode(), request.getPhone(), record.getUniqueId(), record);
            LoggerManager.info("校验用户中心数据 => 结束");

            LoggerManager.info("校验游物欧品卡帐户 => 开始");
            registerAccount(tid, request.getPhone(), userId, record);
            LoggerManager.info("校验游物欧品卡帐户 => 结束");

            if (WxConstants.isWxApplet()) {
                //小程序登录门户
                LoggerManager.info("1.登录门户系统 => 开始");
                loginToWapOfApplet(request.getAppletCode(), request.getAppletEncryptedData(), request.getAppletIv(), request.getOpenId(), record);
                LoggerManager.info("1.登录门户系统 => 结束");
            } else {
                //公众号登录门户
                if (!StringUtil.isNullOrEmpty(record.getRefreshToken())) {
                    WxUserInfo userInfo = wxService.getUserInfoByRefreshToken(record.getRefreshToken(), record.getOpenId());
                    LoggerManager.info("2.登录门户系统 => 开始");
                    loginToWapOfNormal(userInfo.getAccessToken(),  request.getOpenId(), record);
                    LoggerManager.info("2.登录门户系统 => 结束");
                } else {
                    LoggerManager.info("3.登录门户系统 => 开始");
                    loginToWapOfWx(record.getUniqueId(), record.getNickname(), record.getHeadimgurl(), record);
                    LoggerManager.info("3.登录门户系统 => 结束");
                }
            }*/

            record.setStatus(CardActiveRecord.STATUS_APPLIED);
        } catch (ErrorCodeException e) {
            if (record != null) {
                record.setStatus(CardActiveRecord.STATUS_APPLYERR);
                record.setErrMsg(e.getMessage());
            }
            throw e;
        } catch (Exception e) {
            if (record != null) {
                record.setStatus(CardActiveRecord.STATUS_APPLYERR);
                record.setErrMsg("未知错误：" + e.getMessage());
            }
            throw new RuntimeException(e);
        } finally {
            if (record != null) {
                cardActiveRecordService.edit(record);
            }
        }
    }

    private void loginToWapOfNormal(String accessToken, String uniqueId, CardActiveRecord record) {
        MultiValueMap<String, Object> loginMap = new LinkedMultiValueMap<>();
        loginMap.add("authcode", accessToken);
        loginMap.add("type", 1);
        loginMap.add("openid", uniqueId);
        HttpEntity<MultiValueMap<String, Object>> loginRequest = new HttpEntity<>(loginMap, null);
        LoginWapResult result = restTemplate.postForObject(URL_LOGIN4WAP, loginRequest, LoginWapResult.class);
        if (result.isSuccess() && result.getData() != null) {
            record.setWapToken(result.getData().getToken());
        } else {
            record.setErrMsg(result.getMsg());
            throw new ActiveMemberCardException(result.getMsg());
        }
    }

    private void loginToWapOfWx(String uniqueId, String nickName, String headerImgUrl, CardActiveRecord record) {
        //token已过期，使用微信自动登录
        Map<String, String> loginCodeMap = new HashMap<>();
        loginCodeMap.put("unionid", uniqueId);
        loginCodeMap.put("nickname", nickName);
        loginCodeMap.put("headimgurl", headerImgUrl);
        String loginCode;
        try {
            loginCode = Base64Utils.encodeToString(JsonUtil.toJson(loginCodeMap).getBytes("utf-8"));
            LoggerManager.debug("loginCode:" + loginCode);

            MultiValueMap<String, Object> loginMap = new LinkedMultiValueMap<>();
            loginMap.add("type", 1);
            loginMap.add("openid", uniqueId);
            loginMap.add("source", 0);
            loginMap.add("logincode", loginCode);
            HttpEntity<MultiValueMap<String, Object>> loginRequest = new HttpEntity<>(loginMap, null);
            LoginWapResult result = restTemplate.postForObject(URL_LOGIN4WAP, loginRequest, LoginWapResult.class);

            if (result.isSuccess() && result.getData() != null) {
                record.setWapToken(result.getData().getToken());
            } else {
                record.setErrMsg(result.getMsg());
                throw new ActiveMemberCardException(result.getMsg());
            }
        } catch (UnsupportedEncodingException e) {
            LoggerManager.error(e);
            record.setErrMsg(e.getMessage());
            throw new ActiveMemberCardException(e.getMessage());
        }
    }

    private void loginToWapOfApplet(String code, String encryptedData, String iv, String uniqueId, CardActiveRecord record) {
        MultiValueMap<String, Object> loginMap = new LinkedMultiValueMap<>();
        loginMap.add("authcode", "authcode");
        loginMap.add("type", 1);
        loginMap.add("openid", uniqueId);
        loginMap.add("source", "0");
        loginMap.add("wechatappcode", code);
        loginMap.add("encryptedData", encryptedData);
        loginMap.add("iv", iv);
        HttpEntity<MultiValueMap<String, Object>> loginRequest = new HttpEntity<>(loginMap, null);
        LoginWapResult result = restTemplate.postForObject(URL_LOGIN4WAP, loginRequest, LoginWapResult.class);
        if (result.isSuccess() && result.getData() != null) {
            record.setWapToken(result.getData().getToken());
        } else {
            record.setErrMsg(result.getMsg());
            throw new ActiveMemberCardException(result.getMsg());
        }
    }

    private double registerAccount(String tid, String phone, String userId, CardActiveRecord record) {
        MultiValueMap<String, Object> checkUserMap = new LinkedMultiValueMap<String, Object>();
        checkUserMap.add("tid", tid);
        checkUserMap.add("userId", userId);
        checkUserMap.add("phone", phone);
        checkUserMap.add("md5str", MD5Util.getMD5String("wallet" + tid + userId + phone));
        HttpEntity<MultiValueMap<String, Object>> checkUserRequest = new HttpEntity<MultiValueMap<String, Object>>(checkUserMap, null);
        StateResult checkUserResult = restTemplate.postForObject(URL_ACCOUNT_CHECKUSER, checkUserRequest, StateResult.class);

        double balance = 0;
        String type = "1";
        if (checkUserResult.getState() == 0) {
            // 游物欧品帐户不存在，注册帐户
            MultiValueMap<String, Object> accountRegisterMap = new LinkedMultiValueMap<String, Object>();
            accountRegisterMap.add("tid", tid);
            accountRegisterMap.add("type", type);
            accountRegisterMap.add("phone", phone);
            accountRegisterMap.add("userId", userId);
            accountRegisterMap.add("md5str", MD5Util.getMD5String("wallet" + tid + type + phone + userId));
            HttpEntity<MultiValueMap<String, Object>> accountRegisterRequest = new HttpEntity<MultiValueMap<String, Object>>(accountRegisterMap, null);
            StateResult accountRegisterResult = restTemplate.postForObject(URL_ACCOUNT_REGISTER, accountRegisterRequest, StateResult.class);

            if (accountRegisterResult.getState() != 0) {
                throw new ActiveMemberCardException("注册游物欧品卡帐户失败，" + accountRegisterResult.getMsg());
            }
        } else if (checkUserResult.getState() == 1) {
            // 游物欧品帐户存在，查询余额
            MultiValueMap<String, Object> balanceMap = new LinkedMultiValueMap<String, Object>();
            balanceMap.add("tid", tid);
            balanceMap.add("userid", userId);
            balanceMap.add("phone", phone);
            balanceMap.add("md5str", MD5Util.getMD5String("wallet" + tid + userId + phone));
            HttpEntity<MultiValueMap<String, Object>> balanceRequest = new HttpEntity<MultiValueMap<String, Object>>(balanceMap, null);
            GetMyBalanceResult balanceResult = restTemplate.postForObject(URL_ACCOUNT_MYBALANCE, balanceRequest, GetMyBalanceResult.class);

            if (balanceResult.getState() == 0) {
                balance = balanceResult.getMyBalance();
            }
        } else {
            throw new ActiveMemberCardException(checkUserResult.getMsg());
        }

        record.setBalance(balance);
        record.setUserType(type);
        return balance;
    }

    private String registerAndBindUser(String tid, String verifyCode, String verifyKey, boolean ignoreVerifyCode, String phone, String openId, CardActiveRecord record) {
        MultiValueMap<String, Object> registerUserMap = new LinkedMultiValueMap<String, Object>();
        registerUserMap.add("t_id", tid);
        registerUserMap.add("phone", phone);
        registerUserMap.add("openId", openId);
        registerUserMap.add("verificationCode", verifyCode);
        registerUserMap.add("key", verifyKey);
        registerUserMap.add("opType", (ignoreVerifyCode ? 1 : 0));
        HttpEntity<MultiValueMap<String, Object>> registerUserRequest = new HttpEntity<MultiValueMap<String, Object>>(registerUserMap, null);
        RegisterUserResult registerUserResult = restTemplate.postForObject(URL_UC_REGISTER, registerUserRequest, RegisterUserResult.class);

        String userId;
        if (registerUserResult.getState() == ErrorConstants.ERR_UC_REGISTER_PHONE_EXIST) {
            // 用户已存在，查询用户信息
            MultiValueMap<String, Object> findUserMap = new LinkedMultiValueMap<String, Object>();
            findUserMap.add("t_id", "1");
            findUserMap.add("phone", phone);
            HttpEntity<MultiValueMap<String, Object>> findUserRequest = new HttpEntity<MultiValueMap<String, Object>>(findUserMap, null);
            FindUserResult findUserResult = restTemplate.postForObject(URL_UC_FINDUSER, findUserRequest, FindUserResult.class);
            if (findUserResult.isSuccess()) {
                userId = findUserResult.getUserId();
            } else {
                throw new ActiveMemberCardException(findUserResult.getMsg());
            }
        } else if (registerUserResult.getState() == 0) {
            // 用户不存在，且注册成功
            if (registerUserResult.getData() != null) {
                userId = registerUserResult.getData().getUserId();
            } else {
                throw new ActiveMemberCardException("注册用户服务返回空结果");
            }
        } else {
            throw new ActiveMemberCardException("注册用户失败，" + registerUserResult.getMsg());
        }

        record.setUserId(userId);
        return userId;
    }
}
