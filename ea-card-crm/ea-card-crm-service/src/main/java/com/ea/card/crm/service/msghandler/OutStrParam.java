package com.ea.card.crm.service.msghandler;

/**
 * 微信扩展参数OuterStr
 * @author
 */
public class OutStrParam {
    /** 领卡激活 **/
    public static final int ACTION_TYPE_ACTIVE = 1;
    /** 礼品卡赠送 **/
    public static final int ACTION_TYPE_PRESENT = 2;
    /** 会员卡推荐 **/
    public static final int ACTION_TYPE_SHARE = 3;

    private int actionType;
    private String giftCardId;
    private String ownerOpenId;
    private String appletOpenId;

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public String getGiftCardId() {
        return giftCardId;
    }

    public void setGiftCardId(String giftCardId) {
        this.giftCardId = giftCardId;
    }

    public String getOwnerOpenId() {
        return ownerOpenId;
    }

    public void setOwnerOpenId(String ownerOpenId) {
        this.ownerOpenId = ownerOpenId;
    }

    public String getAppletOpenId() {
        return appletOpenId;
    }

    public void setAppletOpenId(String appletOpenId) {
        this.appletOpenId = appletOpenId;
    }
}
