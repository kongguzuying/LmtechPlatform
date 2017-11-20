package com.lmtech.auth.model;

import java.io.Serializable;

/**
 * 帐户信息
 * Created by huang.jb on 2017-1-22.
 */
public class AccountInfo implements Serializable {
    private String accountId;       //帐户编号
    private String accountName;     //帐户名称
    private String loginName;       //登录名
    private String userId;          //用户编号

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
