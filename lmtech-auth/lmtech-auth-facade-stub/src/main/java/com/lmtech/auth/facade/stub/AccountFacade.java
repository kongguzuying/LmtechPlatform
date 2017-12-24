package com.lmtech.auth.facade.stub;

import com.lmtech.auth.facade.request.AccountQueryRequest;
import com.lmtech.auth.facade.request.AccountAuthRequest;
import com.lmtech.auth.facade.request.AccountRegisterRequest;
import com.lmtech.auth.facade.response.AccountInfoListResponse;
import com.lmtech.auth.facade.response.AuthResultResponse;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 帐户相关入口
 * Created by huang.jb on 2017-1-12.
 */
@FeignClient(name = "lmtech-auth")
@RequestMapping(value = "/account")
public interface AccountFacade {
    /**
     * 注册帐户
     * @param request
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    NormalResponse register(AccountRegisterRequest request);

    /**
     * 认证帐户
     * @param request
     * @return
     */
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    AuthResultResponse authenticate(AccountAuthRequest request);

    /**
     * 注销登录
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    NormalResponse logout(StringRequest request);

    /**
     * 获取帐户信息列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/getAccountInfoList", method = RequestMethod.POST)
    AccountInfoListResponse getAccountInfoList(AccountQueryRequest request);
}
