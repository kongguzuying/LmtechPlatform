package com.lmtech.facade.request;

import java.io.Serializable;

/**
 * 请求信息
 * Created by huang.jb on 2017-1-11.
 */
public class RequestInfo implements Serializable {
    private String sysVersion;      //系统版本
    private String appVersion;      //应用版本
    private String account;         //请求帐户
    private String address;         //请求地圵
    private String userId;          //请求用户编号
    private String userName;        //请求用户名称
    private String comment;         //请求说明

    public String getSysVersion() {
        return sysVersion;
    }

    public void setSysVersion(String sysVersion) {
        this.sysVersion = sysVersion;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
