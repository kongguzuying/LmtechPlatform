package com.lmtech.auth.facade.dto;

public class AccountAuthData {
    private String loginName;       //登录名
    private String password;        //密码

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
