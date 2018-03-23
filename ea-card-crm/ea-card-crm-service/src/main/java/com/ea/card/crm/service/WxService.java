package com.ea.card.crm.service;

import com.ea.card.crm.service.vo.PageAuthResponse;
import com.ea.card.crm.service.vo.WxDecryptCodeResponse;
import com.ea.card.crm.service.vo.WxResponseBase;
import com.ea.card.crm.service.vo.WxUserInfo;

import java.util.Map;

/**
 * 微信服务
 */
public interface WxService {
    /**
     * 获取微信AccessToken
     * @return
     */
    String getAccessToken();

    /**
     * 获取票据
     * type 类型
     * @return
     */
    String getTicket(String type);

    /**
     * 获取用户信息
     * @param authAccessToken
     * @param openId
     * @return
     */
    WxUserInfo getUserInfo(String authAccessToken, String openId);

    /**
     * 获取已关注公众号的用户信息
     * @param openId
     * @return
     */
    WxUserInfo getUserInfoOfOfficial(String openId);

    /**
     * 通过RefreshToken获取用户信息
     * @param refreshToken
     * @param openId
     * @return
     */
    WxUserInfo getUserInfoByRefreshToken(String refreshToken, String openId);

    /**
     * 获取jsapi签名
     * @param pageUrl
     * @return
     */
    Map<String, String> getJsApiSign(String pageUrl);
    
    /**
     * 获取jsapi_address签名
     * @param accessToken
     * @param pageUrl
     * @return
     */
    Map<String, String> getJsApiAddressSign(String accessToken, String pageUrl);

    /**
     * 获取微信卡券api签名
     * @param cardId
     * @param code
     * @param openId
     * @return
     */
    Map<String, String> getCardApiSign(String cardId, String code, String openId);

    /**
     * 通过公众号给用户发卡
     * @param officialOpenId
     * @return
     */
    WxResponseBase sendCardToUser(String officialOpenId);

    /**
     * 通过公众号给用户发文本消息
     * @param officialOpenId
     * @param message
     * @return
     */
    WxResponseBase sendTextMessageToUser(String officialOpenId, String message);

    /**
     * 获取微信预支付签名
     * @param prepayId
     * @return
     */
    Map<String, String> getPaySign(String prepayId);

    /**
     * 获取页面授权信息
     * @param code
     * @return
     */
    PageAuthResponse getPageAuthInfo(String code);

    /**
     * 获取小程序页面授权信息
     * @param code
     * @return
     */
    PageAuthResponse getAppletPageAuthInfo(String code);

    /**
     * 刷新页面授权AccessToken
     * @param refreshToken
     * @return
     */
    PageAuthResponse refreshPageAuthAccessToken(String refreshToken);

    /**
     * 解密卡code
     * @param encryptCode
     * @return
     */
    WxDecryptCodeResponse decryptCode(String encryptCode);

    /**
     * 获取卡激活用户填写的信息
     * @param activateTicket
     * @return
     */
    String getActiveTempInfo(String activateTicket);

    /**
     * 调用微信API
     * @param url
     * @param param
     * @return
     */
    String executeApi(String url, String param);

    /**
     * 处理微信消息
     * @param xml
     */
    String handleWxMessag(String xml);

    /**
     * 更新卡片余额
     * @param cardId
     * @param code
     * @param balance
     * @param message
     * @param notify
     */
    void updateCardBalance(String cardId, String code, double balance, String message, boolean notify);

    /**
     * 更新卡片积分
     * @param cardId
     * @param code
     * @param bonus
     * @param message
     * @param notify
     */
    void updateCardBonus(String cardId, String code, long bonus, String message, boolean notify);

    /**
     * 更新卡片等级
     * @param cardId
     * @param code
     * @param mLevelName
     * @param mLevel
     * @param backgroundUrl
     * @param message
     * @param notify
     */
    void updateCardLevel(String cardId, String code, String mLevelName, int mLevel, String backgroundUrl, String message, boolean notify);
}
