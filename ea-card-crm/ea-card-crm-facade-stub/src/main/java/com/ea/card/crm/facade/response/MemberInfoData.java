package com.ea.card.crm.facade.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class MemberInfoData {
    private String userId;
    private String phone;
    private String openId;
    private String code;
    private String membershipNum;
    private long bonus;
    private int mLevel;
    private Date beginDate;
    private Date endDate;
    private boolean forever;
    private String cardBackground;
    private boolean trail;
    private boolean trailing;
    private Date trailDate;
    private double overTimeDays;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMembershipNum() {
        return membershipNum;
    }

    public void setMembershipNum(String membershipNum) {
        this.membershipNum = membershipNum;
    }

    public long getBonus() {
        return bonus;
    }

    public void setBonus(long bonus) {
        this.bonus = bonus;
    }

    public int getmLevel() {
        return mLevel;
    }

    public void setmLevel(int mLevel) {
        this.mLevel = mLevel;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isForever() {
        return forever;
    }

    public void setForever(boolean forever) {
        this.forever = forever;
    }

    public String getCardBackground() {
        return cardBackground;
    }

    public void setCardBackground(String cardBackground) {
        this.cardBackground = cardBackground;
    }

    public boolean isTrail() {
        return trail;
    }

    public void setTrail(boolean trail) {
        this.trail = trail;
    }

    public boolean isTrailing() {
        return trailing;
    }

    public void setTrailing(boolean trailing) {
        this.trailing = trailing;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getTrailDate() {
        return trailDate;
    }

    public void setTrailDate(Date trailDate) {
        this.trailDate = trailDate;
    }

    public double getOverTimeDays() {
        return overTimeDays;
    }

    public void setOverTimeDays(double overTimeDays) {
        this.overTimeDays = overTimeDays;
    }
}
