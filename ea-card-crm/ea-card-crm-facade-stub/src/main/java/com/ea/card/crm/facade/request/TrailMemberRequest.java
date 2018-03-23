package com.ea.card.crm.facade.request;

public class TrailMemberRequest {
    private String openId;
    private int mLevel;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public int getmLevel() {
        return mLevel;
    }

    public void setmLevel(int mLevel) {
        this.mLevel = mLevel;
    }
}
