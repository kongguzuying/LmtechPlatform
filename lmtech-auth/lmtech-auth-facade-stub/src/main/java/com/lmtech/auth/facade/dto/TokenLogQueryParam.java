package com.lmtech.auth.facade.dto;

public class TokenLogQueryParam {
    private String token;       //token值
    private String account;     //申请的帐户
    private String status;      //状态

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
