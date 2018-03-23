package com.ea.card.crm.admin.facade.request;

public class CardPresentRequest {
    private String openId;
    private String cardCategoryId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getCardCategoryId() {
        return cardCategoryId;
    }

    public void setCardCategoryId(String cardCategoryId) {
        this.cardCategoryId = cardCategoryId;
    }
}
