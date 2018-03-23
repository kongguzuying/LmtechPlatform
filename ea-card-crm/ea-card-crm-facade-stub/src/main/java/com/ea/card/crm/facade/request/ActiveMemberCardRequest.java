package com.ea.card.crm.facade.request;

public class ActiveMemberCardRequest {
    /** 分享领卡激活 **/
    public static final int ACTION_TYPE_SHARECARD = 1;
    /** 礼品卡购买赠送 **/
    public static final int ACTION_TYPE_PAYCARD = 2;
    /** 大礼包赠送 **/
    public static final int ACTION_TYPE_GIFTPRESENT = 3;
    /** 公众号领卡激活 */
    public static final int ACTION_TYPE_OFFICIAL = 4;

    private int actionType = ACTION_TYPE_SHARECARD; //1.分享领卡激活，2.礼品卡购买赠送，3.大礼包赠送
    private String verifyKey;           //验证码key
    private String verifyCode;          //验证码
    private boolean ignoreVerifyCode;   //是否忽略验证码
    private String openId;              //openid
    private String phone;               //手机号
    private String presentRecordId;     //赠送记录id
    private String ownerUnionId;        //赠送人unionid
    private String appletCode;          //小程序授权code
    private String appletEncryptedData; //小程序授权加密串
    private String appletIv;            //小程序授权Iv
    private String code;                //卡code

    public String getVerifyKey() {
        return verifyKey;
    }

    public void setVerifyKey(String verifyKey) {
        this.verifyKey = verifyKey;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public boolean isIgnoreVerifyCode() {
        return ignoreVerifyCode;
    }

    public void setIgnoreVerifyCode(boolean ignoreVerifyCode) {
        this.ignoreVerifyCode = ignoreVerifyCode;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public String getPresentRecordId() {
        return presentRecordId;
    }

    public void setPresentRecordId(String presentRecordId) {
        this.presentRecordId = presentRecordId;
    }

	public String getOwnerUnionId() {
		return ownerUnionId;
	}

	public void setOwnerUnionId(String ownerUnionId) {
		this.ownerUnionId = ownerUnionId;
	}

    public String getAppletCode() {
        return appletCode;
    }

    public void setAppletCode(String appletCode) {
        this.appletCode = appletCode;
    }

    public String getAppletEncryptedData() {
        return appletEncryptedData;
    }

    public void setAppletEncryptedData(String appletEncryptedData) {
        this.appletEncryptedData = appletEncryptedData;
    }

    public String getAppletIv() {
        return appletIv;
    }

    public void setAppletIv(String appletIv) {
        this.appletIv = appletIv;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
