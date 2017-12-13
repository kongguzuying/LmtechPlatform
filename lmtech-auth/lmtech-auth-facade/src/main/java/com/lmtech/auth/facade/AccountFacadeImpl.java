package com.lmtech.auth.facade;

import com.lmtech.auth.exceptions.AuthenticateException;
import com.lmtech.auth.facade.dto.AccountAuthData;
import com.lmtech.auth.facade.dto.AccountQueryParam;
import com.lmtech.auth.facade.request.AccountQueryRequest;
import com.lmtech.auth.facade.request.AccountAuthRequest;
import com.lmtech.auth.facade.response.AccountInfoListResponse;
import com.lmtech.auth.facade.response.AuthResultResponse;
import com.lmtech.auth.facade.stub.AccountFacade;
import com.lmtech.auth.model.Account;
import com.lmtech.auth.model.AccountInfo;
import com.lmtech.auth.model.AuthResult;
import com.lmtech.auth.model.Token;
import com.lmtech.auth.service.AccountManager;
import com.lmtech.auth.service.AccountService;
import com.lmtech.auth.service.TokenService;
import com.lmtech.facade.exceptions.ServiceInvokeException;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.util.ServiceRequestUtil;
import com.lmtech.infrastructure.facade.response.UserResponse;
import com.lmtech.infrastructure.facade.stub.UserFacade;
import com.lmtech.infrastructure.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by huang.jb on 2017-1-12.
 */
@RestController
@RequestMapping(value = "/account")
@Api(description = "帐户操作API入口")
public class AccountFacadeImpl implements AccountFacade {

    @Autowired
    private AccountManager accountManager;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserFacade userFacade;

    @Override
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ApiOperation(value = "注册帐户")
    public NormalResponse register(@RequestBody AccountAuthRequest request) {
        //accountManager.add(request.getReqData());

        NormalResponse response = new NormalResponse();
        response.setSuccess(true);
        response.setMessage("帐户注册成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    @ApiOperation(value = "认证帐户")
    public AuthResultResponse authenticate(@RequestBody AccountAuthRequest request) {
        try {
            AccountAuthData account = request.getReqData();

            accountService.authenticate(account.getLoginName(), account.getPassword());
            Account dbAccount = accountService.getByLoginName(account.getLoginName());

            User user = this.getUser(dbAccount.getUserId());
            String currentTimeStr = String.valueOf(System.currentTimeMillis());
            Token token = tokenService.genToken(account.getLoginName(), user.getId(), user.getStoreId(), user.getGroupId(), currentTimeStr);

            AuthResultResponse response = new AuthResultResponse();
            response.setSuccess(true);
            AuthResult authResult = new AuthResult();
            authResult.setAccountInfo(dbAccount.buildAccountInfo());
            authResult.setToken(token);
            response.setData(authResult);
            response.setMessage("帐户认证成功");
            return response;
        } catch (AuthenticateException e) {
            AuthResultResponse response = new AuthResultResponse();
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return response;
        }
    }

    @Override
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ApiOperation(value = "注销登录")
    public NormalResponse logout(@RequestBody StringRequest request) {
        String account = request.getReqData();
        Assert.notNull(account, "帐户名不允许为空");

        tokenService.removeAccountToken(account);

        NormalResponse response = new NormalResponse();
        response.setSuccess(true);
        response.setMessage("注销帐户成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/getAccountInfoList", method = RequestMethod.POST)
    @ApiOperation(value = "获取帐户信息列表")
    public AccountInfoListResponse getAccountInfoList(@RequestBody AccountQueryRequest request) {
        AccountQueryParam accountQueryParam = request.getReqData();
        Account param = new Account();
        //TODO 由accountQueryParam生成param

        List<AccountInfo> accountInfoList = accountService.getAccountInfoList(param);

        AccountInfoListResponse response = new AccountInfoListResponse();
        response.setSuccess(true);
        response.setMessage("获取帐户信息列表成功");
        response.setData(accountInfoList);
        return response;
    }

    private User getUser(String userId) {
        //获取用户信息
        StringRequest getUserRequest = ServiceRequestUtil.buildStringRequest(userId);
        UserResponse response = userFacade.getUser(getUserRequest);
        if (response.isSuccess()) {
            return response.getData();
        } else {
            throw new ServiceInvokeException(userFacade.getClass().getName(), "getUser");
        }
    }
}
