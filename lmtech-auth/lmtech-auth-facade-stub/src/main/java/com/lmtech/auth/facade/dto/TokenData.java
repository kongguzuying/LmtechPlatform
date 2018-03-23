package com.lmtech.auth.facade.dto;

/**
 * token数据
 * @author huang.jb
 */
public class TokenData {
    private String loginName;       //登录名
    private String userId;          //用户id
    private String storeId;         //租户id
    private String groupId;         //群组id
    private String currentTimeStr;  //时间戳
    private String tid;             //业务流水号

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

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getCurrentTimeStr() {
        return currentTimeStr;
    }

    public void setCurrentTimeStr(String currentTimeStr) {
        this.currentTimeStr = currentTimeStr;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }
}
