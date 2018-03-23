package com.ea.card.crm.facade.request;

public class PayPswdChangedRequest {
    private String openId;
    private String smsCode;
    private String oldPayPswd;
    private String paypswd;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getOldPayPswd() {
        return oldPayPswd;
    }

    public void setOldPayPswd(String oldPayPswd) {
        this.oldPayPswd = oldPayPswd;
    }

    public String getPaypswd() {
        return paypswd;
    }

    public void setPaypswd(String paypswd) {
        this.paypswd = paypswd;
    }
}
