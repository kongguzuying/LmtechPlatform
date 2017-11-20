package com.lmtech.admin.common.adaptor;

import com.lmtech.auth.facade.request.AccountRequest;
import com.lmtech.auth.facade.response.AuthResultResponse;
import com.lmtech.auth.facade.stub.AccountFacade;
import com.lmtech.auth.model.Account;
import com.lmtech.auth.model.AuthResult;
import com.lmtech.facade.request.NormalRequest;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.infrastructure.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 帐户服务适配器
 * Created by huang.jb on 2017-3-29.
 */
@Service
public class AccountAdaptor extends ServiceAdaptorBase {

    @Autowired
    private AccountFacade accountFacade;

    @Autowired
    private UserAdaptor userAdaptor;

    /**
     * 用户认证
     * @param loginName
     * @param password
     */
    public User authenticate(String loginName, String password) {
        AccountRequest request = new AccountRequest();
        Account account = new Account();
        account.setLoginName(loginName);
        account.setPassword(password);
        request.setReqData(account);
        initRequest(request);

        AuthResultResponse response = accountFacade.authenticate(request);
        validResponse(response);
        AuthResult authResult = response.getData();
        String userId = authResult.getAccountInfo().getUserId();

        //设置Token
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        httpServletRequest.getSession().setAttribute("LmToken", authResult.getToken().getTokenCode());
        httpServletRequest.getSession().setAttribute("cur_account", authResult.getAccountInfo());

        //获取用户信息
        User user = userAdaptor.get(userId);
        return user;
    }

    /**
     * 修改密码
     * @param userId
     * @param newPassword
     */
    public void changePswd(String userId, String newPassword) {
        //TODO 修改密码
    }

    /**
     * 用户登出
     */
    public void logout() {
        StringRequest request = new StringRequest();
        initRequest(request);

        NormalResponse response = accountFacade.logout(request);
        validResponse(response);
    }
}
