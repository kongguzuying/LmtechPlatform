package com.ea.card.crm.service.vo;

/**
 * 微信删除卡消息
 */
public class WxDeleteCardMessage extends WxMessageBase {
    private String CardId;
    private String UserCardCode;

    public String getCardId() {
        return CardId;
    }

    public void setCardId(String cardId) {
        CardId = cardId;
    }

    public String getUserCardCode() {
        return UserCardCode;
    }

    public void setUserCardCode(String userCardCode) {
        UserCardCode = userCardCode;
    }
}
