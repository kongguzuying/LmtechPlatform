package com.ea.card.crm.service.vo;

public class WxUpdateCardResponse extends WxResponseBase {
    private long result_bonus;
    private double result_balance;
    private String openid;

    public long getResult_bonus() {
        return result_bonus;
    }

    public void setResult_bonus(long result_bonus) {
        this.result_bonus = result_bonus;
    }

    public double getResult_balance() {
        return result_balance;
    }

    public void setResult_balance(double result_balance) {
        this.result_balance = result_balance;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
