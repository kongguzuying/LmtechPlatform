package com.lmtech.auth.facade;

import com.lmtech.auth.facade.request.TokenValidateRequest;
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
import com.lmtech.facade.response.PageDataResponse;
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
            List<String> keys = tokenService.parseToken(token);
            String loginName = keys.get(0);
            Account account = accountService.getByLoginName(loginName);
            response.setData(account.buildAccountInfo());
        }

        return response;
    }

    @Override
    @RequestMapping(value = "/getTokenLogs", method = RequestMethod.POST)
    @ApiOperation(value = "获取Token记录日志")
    public PageDataResponse getTokenLogs(@RequestBody PageRequest<TokenLog, Object> request) {
        PageData pageData = tokenLogService.getActiveTokens(request.getPageParam(), request.getPageIndex(), request.getPageSize());

        PageDataResponse response = new PageDataResponse();
        response.setSuccess(true);
        response.setMessage("获取Token数据日志成功。");
        response.setData(pageData);
        return response;
    }
}
