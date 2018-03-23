package com.ea.card.crm.service.vo;

public class WxApiResponse {
    private long errcode;
    private String errmsg;

    public boolean isSuccess() {
        if (errcode == 0) {
            return true;
        } else {
            return false;
        }
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
