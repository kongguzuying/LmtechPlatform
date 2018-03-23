package com.ea.card.crm.facade.request;

/**
 * 余额支付下单请求
 * @author
 */
public class BalanceOrderRequest {
    private String openId;
    private double totalAmount;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
