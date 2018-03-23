package com.ea.card.crm.facade.request;

/**
 * 卡片领取页面请求
 * @author
 */
public class CardReceivePageRequest {
    private String openId;
    private String presentRecordId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getPresentRecordId() {
        return presentRecordId;
    }

    public void setPresentRecordId(String presentRecordId) {
        this.presentRecordId = presentRecordId;
    }
}
