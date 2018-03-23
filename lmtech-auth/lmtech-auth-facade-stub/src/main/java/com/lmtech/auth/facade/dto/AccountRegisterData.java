package com.lmtech.auth.facade.dto;

public class AccountRegisterData {
    private String loginName;       //登录名
    private String password;        //密码
    private String userId;          //绑定用户id

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
