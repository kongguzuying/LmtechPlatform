package com.ea.card.crm.service;

import com.ea.card.crm.model.MemberRegister;
import com.lmtech.service.DbManagerBase;

/**
 * 会员注册服务接口
 * @author
 */
public interface MemberRegisterService extends DbManagerBase<MemberRegister> {
    /**
     * 通过用户id获取会员注册信息
     * @param userId
     * @return
     */
    MemberRegister getByUserId(String userId);

    /**
     * 通过用户openid获取会员注册信息
     * @param openId
     * @return
     */
    MemberRegister getByOpenId(String openId);

    /**
     * 根据时间获取最新,通过用户openid获取会员注册信息
     * @param openId
     * @return
     */
    MemberRegister getNewByOpenId(String openId);

    /**
     * 通过卡券code获取用户注册信息
     * @param code
     * @return
     */
    MemberRegister getByCode(String code);

    /**
     * 通过用户openid和is_delete获取会员注册信息
     * @param openId
     * @param isDelete
     * @return
     */
    MemberRegister getByOpenIdAndIsDelete(String openId, int isDelete);

    /**
     * 获取已注册成功的openid，用户以小程序注册则返回小程序openid，否则返回公众号openid
     * @param fromUserName 微信消息接收方openid
     * @return
     */
    String getOfficialOpenIdOfRegister(String fromUserName);

    /**
     * 通过用户OfficialOpenId和is_delete获取会员注册信息
     * @param officialOpenId
     * @param isDelete
     * @return
     */
    MemberRegister selectByOfficialOpenIdAndIsDelete(String officialOpenId, int isDelete);

    /**
     * 通过公众号openid获取会员注册信息
     * @param officialOpenId
     * @return
     */
    MemberRegister getByOfficialOpenId(String officialOpenId);

    /**
     * 刷新页面授权RefreshToken
     * @param openId
     * @param refreshToken
     */
    void updateRefreshToken(String openId, String refreshToken);

    /**
     * 通过openid更新is_delete
     * @param openId
     * @param isDelete
     */
    void updateIsDelete(String openId, int isDelete);
    
    /**
     * 通过用户code和is_delete获取会员注册信息
     * @param code
     * @param isDelete
     * @return
     */
    MemberRegister getByCodeAndIsDelete(String code, int isDelete);

    /**
     * 设置试用会员超时
     */
    void setTrailOvertime();
}
