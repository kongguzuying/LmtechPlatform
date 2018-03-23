package com.lmtech.auth.model;

import java.io.Serializable;

/**
 * 认证结果
 * Created by huang.jb on 2017-4-11.
 */
public class AuthResult implements Serializable {
    private AccountInfo accountInfo;
    private Token token;

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
