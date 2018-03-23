package com.lmtech.auth.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 统一登录凭据信息
 * Created by huang.jb on 2017-1-5.
 */
public class Token implements Serializable {
    private String tokenCode;
    private Date expireTime;

    public Token() {

    }

    public Token(String tokenCode) {
        this.tokenCode = tokenCode;
    }

    public String getTokenCode() {
        return tokenCode;
    }

    public void setTokenCode(String tokenCode) {
        this.tokenCode = tokenCode;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}
