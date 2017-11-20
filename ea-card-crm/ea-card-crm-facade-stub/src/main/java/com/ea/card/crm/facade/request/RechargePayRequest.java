package com.ea.card.crm.facade.request;

/**
 * 充值支付请求
 * @author
 */
public class RechargePayRequest {
    private String openId;
    private String officialOpenId;
    private double totalAmount;

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

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
