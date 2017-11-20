package com.ea.card.crm.service.impl;

import com.ea.card.crm.constants.ErrorConstants;
import com.ea.card.crm.dao.MemberRegisterDao;
import com.ea.card.crm.model.MemberRegister;
import com.ea.card.crm.service.CodeAdaptorService;
import com.ea.card.crm.service.MemberRegisterService;
import com.ea.card.crm.service.MemberService;
import com.ea.card.crm.service.WxService;
import com.ea.card.crm.service.exception.NoneRegisterException;
import com.ea.card.crm.service.exception.TokenExpireException;
import com.ea.card.crm.service.exception.TrailMemberException;
import com.ea.card.crm.service.util.Md5Encrypt;
import com.ea.card.crm.service.vo.LoginWapResult;
import com.ea.card.crm.service.vo.PageAuthResponse;
import com.ea.card.crm.service.vo.UCMemberExtResult;
import com.lmtech.common.StateResult;
import com.lmtech.util.IdWorkerUtil;
import com.lmtech.util.JsonUtil;
import com.lmtech.util.LoggerManager;
import com.lmtech.util.StringUtil;
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
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RefreshScope
public class MemberServiceImpl implements MemberService {

	@Value("${service.url_uc_get_member_ext}")
	private String URL_UC_GET_MEMBER_EXT;
	
	@Value("${service.url_login4wap}")
    private String URL_LOGIN4WAP;
	
	@Value("${service.url_relogini4wap}")
    private String URL_RELOGINI4WAP;

    @Value("${service.url_istokenexpire}")
	private String URL_IS_TOKEN_EXPIRE;

	private int trialDays = 30;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private MemberRegisterDao memberRegisterDao;
    @Autowired
    private MemberRegisterService memberRegisterService;
    @Autowired
    private WxService wxService;
    @Autowired
    private CodeAdaptorService codeAdaptorService;

    @Override
    public UCMemberExtResult getUcMemberExt(String userId) {
        String tid = IdWorkerUtil.generateStringId();
        MultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<String, Object>();
        requestMap.add("t_id", tid);
        requestMap.add("userId", userId);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(requestMap, null);
        return restTemplate.postForObject(URL_UC_GET_MEMBER_EXT, request, UCMemberExtResult.class);
    }

    @Override
    @Transactional
    public void trailMember(String openId, int mLevel) {
        MemberRegister register = memberRegisterService.getByOpenId(openId);
        if (register != null) {
            validateTrail(register, mLevel);

            String mLevelName;
            String backgroundUrl = "";
            if (mLevel == MemberRegister.MLEVEL_VPASS) {
                mLevelName = codeAdaptorService.getNameByCodeItemValue(String.valueOf(MemberRegister.MLEVEL_VPASS));
            } else {
                mLevelName = codeAdaptorService.getNameByCodeItemValue(String.valueOf(MemberRegister.MLEVEL_NORMAL));
            }
            memberRegisterDao.updateTrialData(register.getUserId(), mLevel, backgroundUrl, true, new Date(), trialDays);

            //更新微信卡信息
            wxService.updateCardLevel(register.getCardId(), register.getCode(), mLevelName, mLevel, backgroundUrl,null, false);
        } else {
            throw new NoneRegisterException();
        }
    }

    @Override
    public boolean hasTrail(String openId, int mLevel) {
        MemberRegister register = memberRegisterService.getByOpenId(openId);
        validateTrail(register, mLevel);
        return true;
    }

    @Override
    public LoginWapResult loginWap(String openId, String code) {
        MemberRegister register = memberRegisterService.getByOpenId(openId);
        if (register != null) {
            String accessToken;
            if (!StringUtil.isNullOrEmpty(code)) {
                //使用code验证
                PageAuthResponse pageAuthResponse = wxService.getPageAuthInfo(code);
                accessToken = pageAuthResponse.getAccess_token();

                MultiValueMap<String, Object> loginMap = new LinkedMultiValueMap<>();
                loginMap.add("authcode", accessToken);
                loginMap.add("type", 1);
                loginMap.add("openid", register.getUniqueId());
                HttpEntity<MultiValueMap<String, Object>> loginRequest = new HttpEntity<>(loginMap, null);
                //String url = "http://172.29.58.239:8080/jweb_ea_buyer_portals/buyer/user/otherlogin4wap.do";
                LoginWapResult result = restTemplate.postForObject(URL_LOGIN4WAP, loginRequest, LoginWapResult.class);

                memberRegisterService.updateRefreshToken(openId, result.getData().getToken());
                return result;
            } else {
                return reLoginWap(register);
            }
        } else {
            throw new NoneRegisterException();
        }
    }

    @Override
    public LoginWapResult reLoginWap(MemberRegister register) {
        if (StringUtil.isNullOrEmpty(register.getAuthRefreshToken())) {
            return reLoginWapWithWx(register);
        }

        //使用refresh_token验证
        MultiValueMap<String, Object> reLoginMap = new LinkedMultiValueMap<>();
        String tt = IdWorkerUtil.generateStringId();
        String token = register.getAuthRefreshToken();
        reLoginMap.add("vtoken", this.getVToken(register.getUserId(), token, tt));
        reLoginMap.add("tt", tt);
        reLoginMap.add("token", token);
        HttpEntity<MultiValueMap<String, Object>> reLoginRequest = new HttpEntity<>(reLoginMap, null);
        LoginWapResult result = restTemplate.postForObject(URL_RELOGINI4WAP, reLoginRequest, LoginWapResult.class);

        if (result.isSuccess() && !StringUtil.isNullOrEmpty(result.getData().getToken())) {
            memberRegisterService.updateRefreshToken(register.getOpenId(), result.getData().getToken());
            return result;
        } else {
            return reLoginWapWithWx(register);
        }
    }

    @Override
    public LoginWapResult reLoginWapWithWx(MemberRegister register) {
        //token已过期，使用微信自动登录
        Map<String, String> loginCodeMap = new HashMap<>();
        loginCodeMap.put("unionid", register.getUniqueId());
        loginCodeMap.put("nickname", register.getNickname());
        loginCodeMap.put("headimgurl", register.getHeadimgurl());
        String loginCode;
        try {
            loginCode = Base64Utils.encodeToString(JsonUtil.toJson(loginCodeMap).getBytes("utf-8"));
            LoggerManager.debug("loginCode:" + loginCode);

            MultiValueMap<String, Object> loginMap = new LinkedMultiValueMap<>();
            loginMap.add("type", 1);
            loginMap.add("openid", register.getUniqueId());
            loginMap.add("source", 0);
            loginMap.add("logincode", loginCode);
            HttpEntity<MultiValueMap<String, Object>> loginRequest = new HttpEntity<>(loginMap, null);
            //String url = "http://172.29.58.239:8080//buyer/user/otherlogin4wap.do";
            LoginWapResult result = restTemplate.postForObject(URL_LOGIN4WAP, loginRequest, LoginWapResult.class);
            if (result.isSuccess() && !StringUtil.isNullOrEmpty(result.getData().getToken())) {
                memberRegisterService.updateRefreshToken(register.getOpenId(), result.getData().getToken());
            }
            return result;
        } catch (UnsupportedEncodingException e) {
            LoggerManager.error(e);
            LoginWapResult result = new LoginWapResult();
            result.setState(ErrorConstants.ERR_UNKNOW);
            result.setMsg(ErrorConstants.ERR_UNKNOW_MSG);
            return result;
        }
    }

    @Override
    public void validToken(String token) {
        if (StringUtil.isNullOrEmpty(token)) {
            throw new IllegalArgumentException("传入token不允许为空");
        }

        //使用token验证
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("token", token);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, null);
        StateResult result = restTemplate.postForObject(URL_IS_TOKEN_EXPIRE, request, StateResult.class);

        if (!result.isSuccess()) {
            LoggerManager.warn("Token校验失败，token：" + token);
            throw new TokenExpireException();
        }
    }

    public String getVToken(String userId, String token, String tt) {
        String ud = Md5Encrypt.md5(userId);
        String token_md5 = Md5Encrypt.md5(token);
        String tt_md5 = Md5Encrypt.md5(tt);
        String utt_md5 = Md5Encrypt.md5(tt + ud + token); // md5(tt+ud+token)
        String md5 = Md5Encrypt.md5(ud + token_md5 + tt_md5 + utt_md5);
        String base64 = Base64.getEncoder().encodeToString(md5.getBytes());
        return base64;
    }

    private void validateTrail(MemberRegister register, int mLevel) {
        if (register.getmLevel() >= mLevel) {
            throw new TrailMemberException(ErrorConstants.ERR_CRM_TRIAL_LEVEL_HIGHER_MSG, ErrorConstants.ERR_CRM_TRIAL_LEVEL_HIGHER);
        }
        if (register.isTrial()) {
            throw new TrailMemberException(ErrorConstants.ERR_CRM_HAS_TRIAL_MSG, ErrorConstants.ERR_CRM_HAS_TRIAL);
        }
    }
}
