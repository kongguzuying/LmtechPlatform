package com.ea.card.crm.facade.response;

public class GetIntegralData {
    private String userId;//用户ID
    private long sumIntegralNumber;//积分总值
    private int integralMemberLevel;//会员等级 1、普通 2、尊享
    private int toDayNo;//当天
    private int toDaybonus;//当天积分
    private int nextDayNo;//次日
    private int nextDaybonus;//次日积分
    private int signCount;//连续签到次数
    private int isSign;//当日是否已经签到，0-未，1-已

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

    public int getToDayNo() {
        return toDayNo;
    }

    public void setToDayNo(int toDayNo) {
        this.toDayNo = toDayNo;
    }

    public int getToDaybonus() {
        return toDaybonus;
    }

    public void setToDaybonus(int toDaybonus) {
        this.toDaybonus = toDaybonus;
    }

    public int getNextDayNo() {
        return nextDayNo;
    }

    public void setNextDayNo(int nextDayNo) {
        this.nextDayNo = nextDayNo;
    }

    public int getNextDaybonus() {
        return nextDaybonus;
    }

    public void setNextDaybonus(int nextDaybonus) {
        this.nextDaybonus = nextDaybonus;
    }

    public int getSignCount() {
        return signCount;
    }

    public void setSignCount(int signCount) {
        this.signCount = signCount;
    }

    public int getIsSign() {
        return isSign;
    }

    public void setIsSign(int isSign) {
        this.isSign = isSign;
    }
}
