package com.ea.card.crm.service.vo;

public class WxKfCardMessage extends WxKfMessageBase {

    private Wxcard wxcard;

    public Wxcard getWxcard() {
        return wxcard;
    }

    public void setWxcard(Wxcard wxcard) {
        this.wxcard = wxcard;
    }

    public static class Wxcard {
        private String card_id;

        public String getCard_id() {
            return card_id;
        }

        public void setCard_id(String card_id) {
            this.card_id = card_id;
        }
    }
}
