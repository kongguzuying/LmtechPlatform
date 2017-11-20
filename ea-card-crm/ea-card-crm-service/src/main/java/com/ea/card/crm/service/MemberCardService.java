package com.ea.card.crm.service;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.util.Map;

import com.ea.card.crm.facade.request.ActiveMemberCardRequest;
import com.ea.card.crm.facade.request.IncreaseLevelRequest;
import com.ea.card.crm.facade.response.GetSmsCodeResult;
import com.ea.card.crm.service.vo.PageAuthResponse;
import com.lmtech.common.ExeResult;
import com.lmtech.common.StateResult;

/**
 * 会员卡服务
 * @author
 */
public interface MemberCardService {
    /**
     * 用户授权
     * @param code
     * @return
     */
    StateResult userAuth(String code);

	/**
	 * 小程序用户授权
	 * @param code
	 * @param encryptedData
	 * @param iv
	 * @return
	 */
    StateResult appletUserAuth(String code,String encryptedData, String iv) 
    		throws InvalidAlgorithmParameterException, UnsupportedEncodingException;

    /**
     * 获取激活短信验证码
     * @param phone
     * @return
     */
    GetSmsCodeResult getSmsCode(String phone, String appName, Map<String, String> reqParams);

	/**
	 * 激活会员卡
	 * @param request
	 * @param result
	 */
	void activeMemberCardInTrans(ActiveMemberCardRequest request, ExeResult result);

	/**
	 * 提升会员等级参数校验
	 * @param request
	 * @return
	 */
	boolean increaseLevelParamCheck(IncreaseLevelRequest request);
}
