package com.ea.card.crm.facade.request;

import javax.validation.constraints.NotNull;

public class IncreaseLevelRequest {
    @NotNull(message = "openId不能为空")
    private String openId;
    @NotNull(message = "officialOpenId不能为空")
    private String officialOpenId;
    @NotNull(message = "targetLevel不能为空")
    private int targetLevel;
    @NotNull(message = "presentDays不能为空")
    private int presentDays;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getOfficialOpenId() {
        return officialOpenId;
    }

    public void setOfficialOpenId(String officialOpenId) {
        this.officialOpenId = officialOpenId;
    }

    public int getTargetLevel() {
        return targetLevel;
    }

    public void setTargetLevel(int targetLevel) {
        this.targetLevel = targetLevel;
    }

    public int getPresentDays() {
        return presentDays;
    }

    public void setPresentDays(int presentDays) {
        this.presentDays = presentDays;
    }
}
