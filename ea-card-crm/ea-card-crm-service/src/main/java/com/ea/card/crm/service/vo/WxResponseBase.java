package com.ea.card.crm.service.vo;

public class WxResponseBase {
    private long errcode;
    private String errmsg;

    public boolean isSuccess() {
        return errcode == 0;
    }

    public long getErrcode() {
        return errcode;
    }

    public void setErrcode(long errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
