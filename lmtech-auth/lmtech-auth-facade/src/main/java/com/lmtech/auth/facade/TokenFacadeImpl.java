package com.lmtech.auth.facade;

import com.lmtech.auth.constants.AuthErrorConstants;
import com.lmtech.auth.facade.dto.TokenData;
import com.lmtech.auth.facade.dto.TokenLogQueryParam;
import com.lmtech.auth.facade.request.TokenValidateRequest;
import com.lmtech.auth.facade.response.TokenDataResponse;
import com.lmtech.auth.facade.response.TokenValidateResponse;
import com.lmtech.auth.facade.stub.TokenFacade;
import com.lmtech.auth.model.Account;
import com.lmtech.auth.model.Token;
import com.lmtech.auth.model.TokenLog;
import com.lmtech.auth.model.TokenValidateResult;
import com.lmtech.auth.service.AccountService;
import com.lmtech.auth.service.TokenLogService;
import com.lmtech.auth.service.TokenService;
import com.lmtech.common.PageData;
import com.lmtech.facade.request.PageRequest;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.PageDataResponse;
import com.lmtech.util.CollectionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by huang.jb on 2017-1-22.
 */
@RestController
@RequestMapping(value = "/token")
@Api(description = "Token操作API入口")
public class TokenFacadeImpl implements TokenFacade {

    @Autowired
    private TokenLogService tokenLogService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AccountService accountService;

    @Override
    @RequestMapping(value = "/validateToken", method = RequestMethod.POST)
    @ApiOperation(value = "校验Token有效性")
    public TokenValidateResponse validateToken(@RequestBody TokenValidateRequest request) {
        String token = request.getToken();
        boolean returnAccountInfo = request.isReturnAccountInfo();

        //校验token
        TokenValidateResult validateResult = tokenService.validateToken(new Token(token));

        TokenValidateResponse response = new TokenValidateResponse();
        response.setSuccess(validateResult.isValidSuccess());
        response.setMessage(validateResult.getMessage());
        response.setCode(validateResult.getCode());
        if (returnAccountInfo) {
            //获取帐户信息
            TokenData tokenData = this.getTokenData(token);
            Account account = accountService.getByLoginName(tokenData.getLoginName());
            response.setData(account.buildAccountInfo());
        }

        return response;
    }

    @Override
    @RequestMapping(value = "/parseToken", method = RequestMethod.POST)
    @ApiOperation(value = "解析token数据")
    public TokenDataResponse parseToken(StringRequest request) {
        String token = request.getToken();

        TokenDataResponse response = new TokenDataResponse();
        //校验token
        TokenValidateResult validateResult = tokenService.validateToken(new Token(token));
        if (validateResult.isValidSuccess()) {
            //获取token数据
            TokenData tokenData = this.getTokenData(token);
            response.setData(tokenData);
            response.setSuccess(true);
        } else {
            response.setSuccess(false);
            response.setCode(AuthErrorConstants.ERR_TOKEN_VALIDATE);
            response.setMessage(AuthErrorConstants.ERR_TOKEN_VALIDATE_MSG);
        }

        return response;
    }

    @Override
    @RequestMapping(value = "/getTokenLogs", method = RequestMethod.POST)
    @ApiOperation(value = "获取Token记录日志")
    public PageDataResponse getTokenLogs(@RequestBody PageRequest<TokenLogQueryParam, Object> request) {
        TokenLog param = null;
        if (request.getPageParam() != null) {
            param = new TokenLog();
            param.setToken(request.getPageParam().getToken());
            param.setAccount(request.getPageParam().getAccount());
            param.setStatus(request.getPageParam().getStatus());
        }

        PageData pageData = tokenLogService.getActiveTokens(param, request.getPageIndex(), request.getPageSize());

        PageDataResponse response = new PageDataResponse();
        response.setSuccess(true);
        response.setMessage("获取Token数据日志成功。");
        response.setData(pageData);
        return response;
    }

    private TokenData getTokenData(String token) {
        List<String> keys = tokenService.parseToken(token);
        if (!CollectionUtil.isNullOrEmpty(keys) && keys.size() >= 6) {
            String loginName = keys.get(0);
            String userId = keys.get(1);
            String storeId = keys.get(2);
            String groupId = keys.get(3);
            String currentTimeStr = keys.get(4);
            String tid = keys.get(5);

            TokenData tokenData = new TokenData();
            tokenData.setLoginName(loginName);
            tokenData.setUserId(userId);
            tokenData.setStoreId(storeId);
            tokenData.setGroupId(groupId);
            tokenData.setCurrentTimeStr(currentTimeStr);
            tokenData.setTid(tid);
            return tokenData;
        } else {
            return null;
        }
    }
}
