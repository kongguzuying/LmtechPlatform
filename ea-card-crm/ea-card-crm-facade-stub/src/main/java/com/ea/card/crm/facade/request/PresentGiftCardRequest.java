package com.ea.card.crm.facade.request;

import java.util.List;

public class PresentGiftCardRequest {
    private String openId;
    private String orderNo;
    private List<String> giftCardIds;
    private String preId;       //预生成id
    private boolean active;     //是否激活

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public List<String> getGiftCardIds() {
        return giftCardIds;
    }

    public void setGiftCardIds(List<String> giftCardIds) {
        this.giftCardIds = giftCardIds;
    }

    public String getPreId() {
        return preId;
    }

    public void setPreId(String preId) {
        this.preId = preId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
