package com.ea.card.crm.service.constants;

import com.lmtech.common.ContextManager;
import com.lmtech.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 微信常量
 * @author
 */
@Component
@RefreshScope
public class WxConstants {
    public static final String TICKET_TYPE_JSAPI = "jsapi";
    public static final String TICKET_TYPE_WXCARD = "wx_card";
    public static final String TICKET_TYPE_JSAPI_ADDRESS = "jsapi_address";

    /** 领卡事件 **/
    public static final String WX_EVENT_GET_CARD = "user_get_card";
    /** 删卡事件 **/
    public static final String WX_EVENT_DEL_CARD = "user_del_card";
    /** 关注事件 **/
    public static final String WX_EVENT_SUBSCRIBE = "subscribe";
    /** 按钮单击事件 **/
    public static final String WX_EVENT_CLICK = "CLICK";

    /** 发送卡片事件KEY **/
    public static final String WX_EVENT_KEY_SENDCARD = "sendcard";
    /** 在线客服事件KEY **/
    public static final String WX_EVENT_KEY_ONLINE_KF = "online_kf";
    /** 尊享商城事件KEY **/
    public static final String WX_EVENT_KEY_VPASS_SHOP = "vpass_shop";

    @Value("${wx.app_id}")
    public void setAppId(String appId) {
        WX_APP_ID = appId;
    }
    public static String WX_APP_ID;
    @Value("${wx.secret}")
    public void setSecret(String secret) {
        WX_SECRET = secret;
    }
    public static String WX_SECRET;
    @Value("${wx.card_id}")
    public void setCardId(String cardId) {
        WX_CARD_ID = cardId;
    }
    public static String WX_CARD_ID;
    @Value("${wx.pay_api_key}")
    public void setPayApiKey(String payApiKey) {
        WX_PAY_API_KEY = payApiKey;
    }
    public static String WX_PAY_API_KEY;

    @Value("${wx.applet_app_id}")
    public void setAppletAppId(String appletAppId) {
        WX_APPLET_APP_ID = appletAppId;
    }
    public static String WX_APPLET_APP_ID;
    @Value("${wx.applet_secret}")
    public void setAppletSecret(String appletSecret) {
        WX_APPLET_SECRET = appletSecret;
    }
    public static String WX_APPLET_SECRET;
    @Value("${wx.applet_card_id}")
    public void setAppletCardId(String appletCardId) {
        WX_APPLET_CARD_ID = appletCardId;
    }
    public static String WX_APPLET_CARD_ID;
    @Value("${wx.applet_pay_api_key}")
    public void setAppletPayApiKey(String appletPayApiKey) {
        WX_APPLET_PAY_API_KEY = appletPayApiKey;
    }
    public static String WX_APPLET_PAY_API_KEY;

    /*
    TEST

    public static String WX_APP_ID = "wx37aec23e319cd88c";
    public static String WX_SECRET = "67c76d0bcf9df19fbda19ca214e5a1bc";
    public static String WX_CARD_ID = "p-yJVw_Vv3Lcba419cpTcLuDp-D0";
    public static String WX_PAY_API_KEY = "jxM3RaA1qRB4ovxZlOZ1MKJZiwPTMvic";*/


    public static String getWxAppId() {
        if (isWxApplet()) {
            return WX_APPLET_APP_ID;
        } else {
            return WX_APP_ID;
        }
    }
    public static String getWxSecret() {
        if (isWxApplet()) {
            return WX_APPLET_SECRET;
        } else {
            return WX_SECRET;
        }
    }
    public static String getWxPayApiKey() {
        if (isWxApplet()) {
            return WX_APPLET_PAY_API_KEY;
        } else {
            return WX_PAY_API_KEY;
        }
    }

    public static boolean isWxApplet() {
        String appType = ContextManager.getValue("appType");
        return !StringUtil.isNullOrEmpty(appType) && appType.equalsIgnoreCase("wxApplet");
    }
}
