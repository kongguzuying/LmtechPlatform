package com.ea.card.crm.model;

import java.util.Date;

public class IntegralDO {
    private String userId;
    private int integralSource;
    private int isSign;
    private  int dayCount;
    private int signCount;
    private Date updateTime;
    private String tId;
    private int integralType;
    private String integralOrderId;
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getIntegralSource() {
        return integralSource;
    }

    public void setIntegralSource(int integralSource) {
        this.integralSource = integralSource;
    }

    public int getIsSign() {
        return isSign;
    }

    public void setIsSign(int isSign) {
        this.isSign = isSign;
    }

    public int getDayCount() {
        return dayCount;
    }

    public void setDayCount(int dayCount) {
        this.dayCount = dayCount;
    }

    public int getSignCount() {
        return signCount;
    }

    public void setSignCount(int signCount) {
        this.signCount = signCount;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

    public int getIntegralType() {
        return integralType;
    }

    public void setIntegralType(int integralType) {
        this.integralType = integralType;
    }

    public String getIntegralOrderId() {
        return integralOrderId;
    }

    public void setIntegralOrderId(String integralOrderId) {
        this.integralOrderId = integralOrderId;
    }
}
