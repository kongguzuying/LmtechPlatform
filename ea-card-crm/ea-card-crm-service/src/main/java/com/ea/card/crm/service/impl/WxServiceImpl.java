package com.ea.card.crm.service.impl;

import com.ea.card.crm.model.CardActiveRecord;
import com.ea.card.crm.service.CardActiveRecordService;
import com.ea.card.crm.service.WxService;
import com.ea.card.crm.service.constants.WxConstants;
import com.ea.card.crm.service.exception.WxIntfInvokeException;
import com.ea.card.crm.service.exception.WxPageAuthException;
import com.ea.card.crm.service.exception.WxPageAuthRefreshException;
import com.ea.card.crm.service.exception.WxUserExpireException;
import com.ea.card.crm.service.msghandler.DeleteCardMessageHandler;
import com.ea.card.crm.service.msghandler.ReceiveCardMessageHandler;
import com.ea.card.crm.service.util.WechatUtil;
import com.ea.card.crm.service.util.WxCardSign;
import com.ea.card.crm.service.util.WxJsSign;
import com.ea.card.crm.service.vo.*;
import com.lmtech.common.ContextManager;
import com.lmtech.common.ExeResult;
import com.lmtech.redis.service.RedisDataService;
import com.lmtech.util.*;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

@Service
@RefreshScope
public class WxServiceImpl implements WxService {

    private String ACCESS_TOKEN_KEY = "wx_access_token";
    private String TICKET_KEY = "ticket_key";
    private String WX_SIGN_KEY = "card_sign_key";
    private int ACCESS_TOKEN_OVERTIME_SECONDS = 360;    //30 minute
    private int TICKET_OVERTIME_SECONDS = 360;    //30 minute
    private int WX_SIGN_OVERTIME_SECONDS = 360;

    private static String getURL_ACCESS_TOKEN_URL() {
        return "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + WxConstants.getWxAppId() + "&secret=" + WxConstants.getWxSecret();
    }
    private static String getURL_TICKET_URL() {
        return "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
    }
    private static String getURL_PAGE_AUTH_ACCESSTOKEN() {
        return "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WxConstants.getWxAppId() + "&secret=" + WxConstants.getWxSecret() + "&grant_type=authorization_code&code=";
    }
    private static String getURL_APPLET_PAGE_AUTH_INFO() {
        return "https://api.weixin.qq.com/sns/jscode2session?appid=" + WxConstants.getWxAppId() + "&secret=" + WxConstants.getWxSecret() + "&grant_type=authorization_code&js_code=";
    }
    private static String getURL_PAGE_AUTH_REFRESH_ACCESSTOKEN() {
        return "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + WxConstants.getWxAppId() + "&grant_type=refresh_token&refresh_token=";
    }
    private static String getURL_WX_USERINFO() {
        return "https://api.weixin.qq.com/sns/userinfo?lang=zh_CN";
    }
    private static String getURL_WX_USERINFO_OFFICIAL() {
        return "https://api.weixin.qq.com/cgi-bin/user/info?lang=zh_CN";
    }
    private static String getURL_WX_UPDATE_USER() {
        return "https://api.weixin.qq.com/card/membercard/updateuser?access_token=";
    }
    private static String getURL_WX_DECRYPT_CODE() { return "https://api.weixin.qq.com/card/code/decrypt?access_token="; }
    private static String getURL_ACTIVATE_TEMP_INFO() {
        return "https://api.weixin.qq.com/card/membercard/activatetempinfo/get?access_token=";
    }
    private static  String getURL_WX_CUSTOMER_SEND() {
        return "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";
    }

    private ConcurrentMap<Object, Object> timeOutMap = CollectionUtil.buildTimeoutMapAfterWrite(ACCESS_TOKEN_OVERTIME_SECONDS, TimeUnit.SECONDS, null);

    @Autowired
    private RedisDataService redisDataService;
    @Autowired
    private ReceiveCardMessageHandler receiveCardMessageHandler;
    @Autowired
    private DeleteCardMessageHandler deleteCardMessageHandler;
    @Autowired
    private CardActiveRecordService cardActiveRecordService;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    @HystrixCommand(fallbackMethod = "getAccessTokenFallBack")
    public String getAccessToken() {
        Object lock = new Object();
        String key = getAppType() + ACCESS_TOKEN_KEY;
        String accessToken = redisDataService.getKey(key);
        if (StringUtil.isNullOrEmpty(accessToken)) {
            synchronized (lock) {
                if (StringUtil.isNullOrEmpty(accessToken)) {
                    AccessTokenResponse response = this.getAccessTokenFromWx();
                    accessToken = response.getAccess_token();
                    redisDataService.setKey(key, accessToken, "NX", ACCESS_TOKEN_OVERTIME_SECONDS);
                    LoggerManager.debug("get new access token:" + accessToken);
                }
            }
        } else {
            LoggerManager.debug("get access token from redis cache, token:" + accessToken);
        }

        return accessToken;
    }

    @Override
    @HystrixCommand(fallbackMethod = "getTicketFallBack")
    public String getTicket(String type) {
        Object lock = new Object();
        String key = getAppType() + TICKET_KEY + ":" + type;
        String ticket = redisDataService.getKey(key);
        if (StringUtil.isNullOrEmpty(ticket)) {
            synchronized (lock) {
                if (StringUtil.isNullOrEmpty(ticket)) {
                    String accessToken = this.getAccessToken();
                    TicketResponse response = this.getTicketFromWx(type, accessToken);
                    ticket = response.getTicket();
                    redisDataService.setKey(key, ticket, "NX", TICKET_OVERTIME_SECONDS);
                    LoggerManager.debug("get new ticket:" + ticket);
                }
            }
        } else {
            LoggerManager.debug("get ticket from redis cache, ticket:" + ticket);
        }

        return ticket;
    }

    @Override
    public WxUserInfo getUserInfo(String authAccessToken, String openId) {
        String url = getURL_WX_USERINFO() + "&access_token=" + authAccessToken + "&openid=" + openId;
        WxUserInfo userInfo = restTemplate.getForObject(url, WxUserInfo.class);
        return userInfo;
    }

    public WxUserInfo getUserInfoOfOfficial(String openId) {
        String url = getURL_WX_USERINFO_OFFICIAL() + "&access_token=" + getAccessToken() + "&openid=" + openId;
        WxUserInfo userInfo = restTemplate.getForObject(url, WxUserInfo.class);
        if (userInfo.getErrcode() != 0) {
            throw new WxIntfInvokeException(userInfo);
        }
        return userInfo;
    }

    @Override
    public WxUserInfo getUserInfoByRefreshToken(String refreshToken, String openId) {
        PageAuthResponse pageAuthResponse = refreshPageAuthAccessToken(refreshToken);
        if (pageAuthResponse.isSuccess()) {
            String url = getURL_WX_USERINFO() + "&access_token=" + pageAuthResponse.getAccess_token() + "&openid=" + openId;
            WxUserInfo userInfo = restTemplate.getForObject(url, WxUserInfo.class);
            userInfo.setAccessToken(pageAuthResponse.getAccess_token());
            LoggerManager.info("wx userinfo:" + JsonUtil.toJson(userInfo));
            return userInfo;
        } else {
            throw new WxUserExpireException();
        }
    }

    @Override
    public Map<String, String> getJsApiSign(String pageUrl) {
        String ticket = this.getTicket(WxConstants.TICKET_TYPE_JSAPI);
        Map<String, String> result = WxJsSign.sign(ticket, pageUrl);
        return result;
    }
    
    @Override
    public Map<String, String> getJsApiAddressSign(String accessToken, String pageUrl) {
        Map<String, String> result = WxJsSign.signAddress(accessToken, pageUrl);
        return result;
    }

    @Override
    public Map<String, String> getCardApiSign(String cardId, String code, String openId) {
        String ticket = this.getTicket(WxConstants.TICKET_TYPE_WXCARD);
        String timestamp = WxJsSign.create_timestamp();
        String noncestr = WxJsSign.create_nonce_str();
        WxCardSign sign = new WxCardSign();
        sign.addData(ticket);
        sign.addData(timestamp);
        sign.addData(cardId);
        sign.addData(code);
        sign.addData(openId);
        sign.addData(noncestr);
        String signature = sign.getSignature();

        Map<String, String> result = new HashMap<String, String>();
        result.put("ticket", ticket);
        result.put("timestamp", timestamp);
        result.put("card_id", cardId);
        result.put("code", code);
        result.put("open_id", openId);
        result.put("noncestr", noncestr);
        result.put("signature", signature);
        return result;
    }

    @Override
    public WxResponseBase sendCardToUser(String officialOpenId) {
        WxKfCardMessage kfCardMessage = new WxKfCardMessage();
        kfCardMessage.setMsgtype(WxKfCardMessage.MSG_TYPE_WXCARD);
        kfCardMessage.setTouser(officialOpenId);
        WxKfCardMessage.Wxcard wxcard = new WxKfCardMessage.Wxcard();
        wxcard.setCard_id(WxConstants.WX_CARD_ID);
        kfCardMessage.setWxcard(wxcard);

        String url = getURL_WX_CUSTOMER_SEND() + getAccessToken();
        WxResponseBase result = restTemplate.postForObject(url, kfCardMessage, WxResponseBase.class);
        LoggerManager.info("已向用户发送会员卡，用户openid：" + officialOpenId);
        return result;
    }

    @Override
    public WxResponseBase sendTextMessageToUser(String officialOpenId, String message) {
        if (StringUtil.isNullOrEmpty(message)) {
            throw new IllegalArgumentException("发送消息不允许为空");
        }

        WxKfTextMessage kfTextMessage = new WxKfTextMessage();
        kfTextMessage.setMsgtype(WxKfTextMessage.MSG_TYPE_TEXT);
        kfTextMessage.setTouser(officialOpenId);
        WxKfTextMessage.Text text = new WxKfTextMessage.Text();
        text.setContent(message);
        kfTextMessage.setText(text);

        String url = getURL_WX_CUSTOMER_SEND() + getAccessToken();
        WxResponseBase result = restTemplate.postForObject(url, kfTextMessage, WxResponseBase.class);
        LoggerManager.info("已向用户发送消息：" + message + "，用户openid：" + officialOpenId);
        return result;
    }

    @Override
    public Map<String, String> getPaySign(String prepayId) {
        SortedMap<String, String> param = new TreeMap<>();
        param.put("package", "prepay_id=" + prepayId);
        param.put("appId", WxConstants.getWxAppId());
        param.put("nonceStr", WechatUtil.createNoncestr(20));
        param.put("timeStamp", (System.currentTimeMillis() / 1000) + "");
        param.put("signType", "MD5");
        String paySign = WechatUtil.createSign(param);
        param.put("paySign", paySign);
        param.put("prepayId", prepayId);
        return param;
    }

    @Override
    public PageAuthResponse getPageAuthInfo(String code) {
        //从微信获取
        PageAuthResponse response = restTemplate.getForObject(getURL_PAGE_AUTH_ACCESSTOKEN() + code, PageAuthResponse.class);
        if (response.isSuccess()) {
            return response;
        } else {
            throw new WxPageAuthException();
        }
    }

    @Override
    public PageAuthResponse getAppletPageAuthInfo(String code) {
        //从微信获取
        PageAuthResponse response = restTemplate.getForObject(getURL_APPLET_PAGE_AUTH_INFO() + code, PageAuthResponse.class);
        if (response.isSuccess()) {
            return response;
        } else {
            throw new WxPageAuthException();
        }
    }

    @Override
    public PageAuthResponse refreshPageAuthAccessToken(String refreshToken) {
        String url = getURL_PAGE_AUTH_REFRESH_ACCESSTOKEN() + refreshToken;
        LoggerManager.debug("登录凭证 code 获取微信 session_key 和 openid："+url);
        PageAuthResponse response = restTemplate.getForObject(url, PageAuthResponse.class);
        LoggerManager.debug("获取微信 session_key 和 openid返回的结果集："+JsonUtil.toJson(response));
        if (response.isSuccess()) {
            return response;
        } else { 
            throw new WxPageAuthRefreshException();
        }
    }

    @Override
    public WxDecryptCodeResponse decryptCode(String encryptCode) {
        String accessToken = this.getAccessToken();
        String url = (getURL_WX_DECRYPT_CODE() + accessToken);
        String param = ("{\"encrypt_code\":\"" + encryptCode + "\"}");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type", "application/json;charset=UTF-8");
        HttpEntity<String> httpEntity = new HttpEntity<>(param, headers);
        WxDecryptCodeResponse result = restTemplate.postForObject(url, httpEntity, WxDecryptCodeResponse.class);
        return result;
    }

    @Override
    public String getActiveTempInfo(String activateTicket) {
        String accessToken = this.getAccessToken();
        String url = getURL_ACTIVATE_TEMP_INFO() + accessToken;
        String param = "{\"activate_ticket\":\"" + activateTicket + "\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type", "application/json;charset=UTF-8");
        HttpEntity<String> httpEntity = new HttpEntity<String>(param, headers);
        String result = restTemplate.postForObject(url, httpEntity, String.class);
        return result;
    }

    @Override
    @HystrixCommand(fallbackMethod = "executeApiFallBack")
    public String executeApi(String url, String param) {
        String accessToken = this.getAccessToken();
        url += ("?access_token=" + accessToken);
        param = StringUtil.replaceBlank(param);
        LoggerManager.debug(url);
        LoggerManager.debug(param);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type", "application/json;charset=UTF-8");
        HttpEntity<String> httpEntity = new HttpEntity<String>(param, headers);
        String result = restTemplate.postForObject(url, httpEntity, String.class);
        LoggerManager.debug(result);
        return result;
    }

    @Override
    public String handleWxMessag(String xml) {
        WxMessageBase messageBase = XmlUtil.fromXml(xml, "xml", WxMessageBase.class);
        if (equalsEvent(messageBase, WxConstants.WX_EVENT_GET_CARD)) {
            try {
                //用户领卡事件
                LoggerManager.info("处理会员卡激活事件 => 开始");
                WxReceiveCardMessage message = XmlUtil.fromXml(xml, "xml", WxReceiveCardMessage.class);
                if (!StringUtil.isNullOrEmpty(message.getSourceScene()) && message.getSourceScene().equals(WxReceiveCardMessage.SOURCE_SCENE_LANDING_PAGE)) {
                    LoggerManager.info("先领卡再激活模式，忽略激活处理事件。");
                } else {
                    receiveCardMessageHandler.handle(message);
                }
                LoggerManager.info("处理会员卡激活事件 => 结束");
            } catch (Exception e) {
                Object recordObject = ContextManager.getValue("card_active_record");
                if (recordObject != null) {
                    CardActiveRecord record = (CardActiveRecord) recordObject;
                    record.setStatus(CardActiveRecord.STATUS_ACTIVEERR);
                    record.setErrMsg(e.getMessage());
                    cardActiveRecordService.edit(record);
                }
                throw e;
            }
        } else if (equalsEvent(messageBase, WxConstants.WX_EVENT_DEL_CARD)) {
            LoggerManager.info("处理会员卡删除事件 => 开始");
            WxDeleteCardMessage message = XmlUtil.fromXml(xml, "xml", WxDeleteCardMessage.class);
            deleteCardMessageHandler.handle(message);
            LoggerManager.info("处理会员卡删除事件 => 开始");
        } else if (equalsEvent(messageBase, WxConstants.WX_EVENT_SUBSCRIBE)) {
            LoggerManager.info("处理公众号关注事件 => 开始");
            sendCardToUser(messageBase.getFromUserName());
            //TODO 配置在系统参数中
            String message = "嗨，欢迎关注，小星总算等到您啦！花的更少，过得更好，星链通为您开启品质生活！";
            //sendTextMessageToUser(messageBase.getFromUserName(), message);
            LoggerManager.info("处理公众号关注事件 => 结束");
        } else if (equalsEvent(messageBase, WxConstants.WX_EVENT_CLICK)) {
            LoggerManager.info("处理按钮点击事件 => 开始");
            handleWxClickEvent(messageBase);
            LoggerManager.info("处理按钮点击事件 => 结束");
        }
        return null;
    }

    private boolean equalsEvent(WxMessageBase messageBase, String event) {
        return messageBase.getMsgType() != null && messageBase.getMsgType().equalsIgnoreCase("event") && messageBase.getEvent() != null && messageBase.getEvent().equalsIgnoreCase(event);
    }

    private void handleWxClickEvent(WxMessageBase messageBase) {
        String eventKey = messageBase.getEventKey();
        if (WxConstants.WX_EVENT_KEY_SENDCARD.equalsIgnoreCase(eventKey)) {
            //向用户发送会员卡
            sendCardToUser(messageBase.getFromUserName());
        } else if (WxConstants.WX_EVENT_KEY_ONLINE_KF.equalsIgnoreCase(eventKey)) {
            //TODO 配置在系统参数中
            String message = "很高兴为您服务，请拨打客户服务热线075588392837与我们联系，谢谢！";
            sendTextMessageToUser(messageBase.getFromUserName(), message);
        } else if (WxConstants.WX_EVENT_KEY_VPASS_SHOP.equalsIgnoreCase(eventKey)) {
            //TODO 暂时不处理
        } else {
            LoggerManager.warn("未知的click事件key：" + eventKey);
        }
    }

    @Override
    public void updateCardBalance(String cardId, String code, double balance, String message, boolean notify) {
        String accessToken = this.getAccessToken();
        String url = getURL_WX_UPDATE_USER() + accessToken;

        WxUpdateCardRequest request = new WxUpdateCardRequest();
        request.setBalance(balance * 100);      //微信服务端以分为单位
        request.setRecord_balance(message);
        request.setCard_id(cardId);
        request.setCode(code);
        WxUpdateCardRequest.NotifyOptional notifyOptional = new WxUpdateCardRequest.NotifyOptional();
        notifyOptional.setIs_notify_balance(notify);
        request.setNotify_optional(notifyOptional);

        WxUpdateCardResponse response = restTemplate.postForObject(url, request, WxUpdateCardResponse.class);
        if (!response.isSuccess()) {
            throw new WxIntfInvokeException(response);
        }
    }

    @Override
    public void updateCardBonus(String cardId, String code, long bonus, String message, boolean notify) {
        String accessToken = this.getAccessToken();
        String url = getURL_WX_UPDATE_USER() + accessToken;

        WxUpdateCardRequest request = new WxUpdateCardRequest();
        request.setAdd_bonus(10L);
        request.setBonus(bonus);
        request.setRecord_bonus(message);
        request.setCard_id(cardId);
        request.setCode(code);
        WxUpdateCardRequest.NotifyOptional notifyOptional = new WxUpdateCardRequest.NotifyOptional();
        notifyOptional.setIs_notify_bonus(notify);
        request.setNotify_optional(notifyOptional);

        WxUpdateCardResponse response = restTemplate.postForObject(url, request, WxUpdateCardResponse.class);
        if (!response.isSuccess()) {
            throw new WxIntfInvokeException(response);
        }
    }

    @Override
    public void updateCardLevel(String cardId, String code, String mLevelName, int mLevel, String backgroundUrl, String message, boolean notify) {
        String accessToken = this.getAccessToken();
        String url = getURL_WX_UPDATE_USER() + accessToken;

        WxUpdateCardRequest request = new WxUpdateCardRequest();
        request.setCustom_field_value1(mLevelName);
        request.setCard_id(cardId);
        request.setCode(code);
        if(!StringUtil.isNullOrEmpty(backgroundUrl)) {
        	request.setBackground_pic_url(backgroundUrl);
        }
        WxUpdateCardRequest.NotifyOptional notifyOptional = new WxUpdateCardRequest.NotifyOptional();
        notifyOptional.setIs_notify_custom_field1(notify);
        request.setNotify_optional(notifyOptional);

        WxUpdateCardResponse response = restTemplate.postForObject(url, request, WxUpdateCardResponse.class);
        if (!response.isSuccess()) {
            throw new WxIntfInvokeException(response);
        }
    }

    public String getAccessTokenFallBack() {
        String accessTokenStr;
        String key = getAppType() + ACCESS_TOKEN_KEY;
        Object accessToken = timeOutMap.get(key);
        if (accessToken == null || StringUtil.isNullOrEmpty(accessToken.toString())) {
            AccessTokenResponse response = this.getAccessTokenFromWx();
            accessTokenStr = response.getAccess_token();
            timeOutMap.put(key, accessTokenStr);
            LoggerManager.debug("fallback: get new access token:" + accessToken);
            return accessTokenStr;
        } else {
            accessTokenStr = accessToken.toString();
            LoggerManager.debug("fallback: get access token from local cache, token:" + accessTokenStr);
            return accessTokenStr;
        }
    }

    public String getTicketFallBack(String type) {
        String ticketStr;
        String key = TICKET_KEY + ":" + type;
        Object ticket = timeOutMap.get(key);
        if (ticket == null || StringUtil.isNullOrEmpty(ticket.toString())) {
            String accessToken = this.getAccessToken();
            TicketResponse response = this.getTicketFromWx(type, accessToken);
            ticketStr = response.getTicket();
            timeOutMap.put(key, ticketStr);
            LoggerManager.debug("fallback: get new ticket:" + ticketStr);
            return ticketStr;
        } else {
            ticketStr = ticket.toString();
            LoggerManager.debug("fallback: get ticket from local cache, ticket:" + ticketStr);
            return ticketStr;
        }
    }

    public String executeApiFallBack(String url, String param) {
        ExeResult result = new ExeResult();
        result.setSuccess(false);
        result.setMessage("调用微信接口出错，请稍后再试。");

        return JsonUtil.toJson(result.getResult());
    }

    protected AccessTokenResponse getAccessTokenFromWx() {
        //从微信获取
        AccessTokenResponse response = restTemplate.getForObject(getURL_ACCESS_TOKEN_URL(), AccessTokenResponse.class);
        return response;
    }

    private TicketResponse getTicketFromWx(String type, String accessToken) {
        //从微信获取
        String param = ("?type=" + type + "&access_token=" + accessToken);
        TicketResponse response = restTemplate.getForObject(getURL_TICKET_URL() + param, TicketResponse.class);
        return response;
    }

    private String getAppType() {
        String appType = ContextManager.getValue("appType");
        if (appType != null) {
            return appType + ":";
        } else {
            return "wx:";
        }
    }
}
