package com.lmtech.infrastructure.vo;

import java.io.Serializable;

/**
 * 用户信息
 * Created by huang.jb on 2017-2-10.
 */
public class UserInfo implements Serializable {
    public static final String REDIS__PREFIX_ACCOUNT_USER = "AccountUserInfo:";

    private String userId;
    private String userName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
