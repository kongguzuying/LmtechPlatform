package com.lmtech.auth.service;

import com.lmtech.auth.model.Token;
import com.lmtech.auth.model.TokenValidateResult;

import java.util.List;

/**
 * 凭据服务
 * Created by huang.jb on 2017-1-5.
 */
public interface TokenService {
    /**
     * 生成凭据
     * @param keys
     * @return
     */
    Token genToken(String... keys);

    /**
     * 转换Token
     * @param tokenCode
     * @return
     */
    List<String> parseToken(String tokenCode);

    /**
     * 移除帐户Token
     * @param account
     */
    void removeAccountToken(String account);

    /**
     * 校验Token
     * @param token
     * @return
     */
    TokenValidateResult validateToken(Token token);
}
