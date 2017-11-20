package com.ea.card.crm.service;

import com.ea.card.crm.model.MemberRegister;
import com.ea.card.crm.service.vo.LoginWapResult;
import com.ea.card.crm.service.vo.UCMemberExtResult;

/**
 * 会员服务
 * @author
 */
public interface MemberService {
    /**
     * 获取UC用户扩展信息
     * @param userId
     * @return
     */
    UCMemberExtResult getUcMemberExt(String userId);

    /**
     * 适用会员
     * @param openId
     * @param mLevel
     */
    void trailMember(String openId, int mLevel);

    /**
     * 能否适用会员
     * @param openId
     * @param mLevel
     * @return
     */
    boolean hasTrail(String openId, int mLevel);

    /**
     * 登录门户系统
     * @param openId
     * @param code
     */
    LoginWapResult loginWap(String openId, String code);

    /**
     * 重新登录门户系统
     * @param register
     * @return
     */
    LoginWapResult reLoginWap(MemberRegister register);

    /**
     * 使用微信自动登录门户系统
     * @param register
     * @return
     */
    LoginWapResult reLoginWapWithWx(MemberRegister register);

    /**
     * 校验token
     * @param token
     */
    void validToken(String token);
}
