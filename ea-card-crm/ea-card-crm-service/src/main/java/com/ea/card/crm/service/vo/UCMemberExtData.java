package com.ea.card.crm.service.vo;

public class UCMemberExtData {
    private String userId;
    private long sumIntegralNumber;
    private int integralMemberLevel;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getSumIntegralNumber() {
        return sumIntegralNumber;
    }

    public void setSumIntegralNumber(long sumIntegralNumber) {
        this.sumIntegralNumber = sumIntegralNumber;
    }

    public int getIntegralMemberLevel() {
        return integralMemberLevel;
    }

    public void setIntegralMemberLevel(int integralMemberLevel) {
        this.integralMemberLevel = integralMemberLevel;
    }
}
