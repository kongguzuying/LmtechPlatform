package com.ea.card.crm.facade.request;

import java.util.List;

/**
 * 礼品卡支付请求
 */
public class GiftCardPayRequest {
    private String openId;
    private String officialOpenId;
    private String cardChildCategoryId;
    private String cardBackground;
    private int cardLevel;
    private String cardLevelName;
    private double totalAmount;
    private int totalNumber;
    private List<GiftCardPayDetail> giftCardPayDetails;
    private String prePresentRecordId;

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

    public String getCardChildCategoryId() {
        return cardChildCategoryId;
    }

    public void setCardChildCategoryId(String cardChildCategoryId) {
        this.cardChildCategoryId = cardChildCategoryId;
    }

    public String getCardBackground() {
        return cardBackground;
    }

    public void setCardBackground(String cardBackground) {
        this.cardBackground = cardBackground;
    }

    public int getCardLevel() {
        return cardLevel;
    }

    public void setCardLevel(int cardLevel) {
        this.cardLevel = cardLevel;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<GiftCardPayDetail> getGiftCardPayDetails() {
        return giftCardPayDetails;
    }

    public void setGiftCardPayDetails(List<GiftCardPayDetail> giftCardPayDetails) {
        this.giftCardPayDetails = giftCardPayDetails;
    }

    public String getCardLevelName() {
        return cardLevelName;
    }

    public void setCardLevelName(String cardLevelName) {
        this.cardLevelName = cardLevelName;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public String getPrePresentRecordId() {
        return prePresentRecordId;
    }

    public void setPrePresentRecordId(String prePresentRecordId) {
        this.prePresentRecordId = prePresentRecordId;
    }
}
