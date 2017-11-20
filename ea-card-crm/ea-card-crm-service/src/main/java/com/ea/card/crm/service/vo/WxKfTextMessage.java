package com.ea.card.crm.service.vo;

/**
 * 微信客户文本消息
 * @author huang.jb
 */
public class WxKfTextMessage extends WxKfMessageBase {

    private Text text;

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public static class Text {
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
