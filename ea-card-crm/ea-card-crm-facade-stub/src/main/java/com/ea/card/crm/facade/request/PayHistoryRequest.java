package com.ea.card.crm.facade.request;

public class PayHistoryRequest {
    private String openId;
    private Boolean isNotPresent;
    private int pageIndex = 1;
    private int pageSize = 20;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Boolean getNotPresent() {
        return isNotPresent;
    }

    public void setNotPresent(Boolean notPresent) {
        isNotPresent = notPresent;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
