package com.ea.card.crm.facade.request;

import javax.validation.constraints.NotNull;

public class CardPresentRequest {
    @NotNull(message = "openId不能为空")
    private String openId;
    @NotNull(message = "cardCategoryId不能为空")
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
