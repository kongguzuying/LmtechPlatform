package com.ea.card.crm.service.vo;

/**
 * 微信客户消息基类
 * @author huang.jb
 */
public class WxKfMessageBase {
    /** 卡片消息 **/
    public static final String MSG_TYPE_WXCARD = "wxcard";
    /** 文本消息 **/
    public static final String MSG_TYPE_TEXT = "text";

    private String touser;
    private String msgtype;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }
}
