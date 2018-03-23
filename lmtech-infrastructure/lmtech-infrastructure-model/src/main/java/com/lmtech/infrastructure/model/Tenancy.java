package com.lmtech.infrastructure.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.DbEntityBase;

/**
 * 租户
 *
 * @author huang.jb
 */
@TableName("lm_tenancy")
public class Tenancy extends DbEntityBase {
    /** 新申请 */
    public static final int STATUS_NEW = 1;
    /** 审核中 */
    public static final int STATUS_CHECKING = 10;
    /** 审核成功 */
    public static final int STATUS_CHECK_SUCCESS = 20;
    /** 审核失败 */
    public static final int STATUS_CHECK_FAILED = 30;
    /** 正常营业 */
    public static final int STATUS_ACTIVE = 40;
    /** 停止营业 */
    public static final int STATUS_DISABLE = 50;
    /** 非法关闭 */
    public static final int STATUS_ILLEGAL_CLOSE = 60;
    /** 过期关闭 */
    public static final int STATUS_TIMEOUT_CLOSE = 70;

    @TableField("code")
    private String code;            //编码
    @TableField("name")
    private String name;            //租户名称
    @TableField("phone")
    private String phone;           //电话
    @TableField("mobile")
    private String mobile;          //手机
    @TableField("qq")
    private String qq;              //qq
    @TableField("weixin")
    private String weixin;          //微信
    @TableField("address")
    private String address;         //真实地址
    @TableField("status")
    private int status;             //状态
    @TableField("info")
    private String info;            //租户信息
    @TableField("user_id")
    private String userId;          //用户id
    @TableField("user_name")
    private String userName;        //用户真实姓名

    @TableField("app_id")
    private String appId;          //公众号appId
    @TableField("secret")
    private String secret;          //公众号secret
    @TableField("card_id")
    private String cardId;         //公众号卡片id
    @TableField("pay_api_key")
    private String payApiKey;      //公众号支付api key
    @TableField("applet_app_id")
    private String appletAppId;     //小程序appId
    @TableField("applet_secret")
    private String appletSecret;    //小程序secret
    @TableField("applet_card_id")
    private String appletCardId;    //小程序卡片id
    @TableField("applet_pay_api_key")
    private String appletPayApiKey; //小程序支付api key

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getPayApiKey() {
        return payApiKey;
    }

    public void setPayApiKey(String payApiKey) {
        this.payApiKey = payApiKey;
    }

    public String getAppletAppId() {
        return appletAppId;
    }

    public void setAppletAppId(String appletAppId) {
        this.appletAppId = appletAppId;
    }

    public String getAppletSecret() {
        return appletSecret;
    }

    public void setAppletSecret(String appletSecret) {
        this.appletSecret = appletSecret;
    }

    public String getAppletCardId() {
        return appletCardId;
    }

    public void setAppletCardId(String appletCardId) {
        this.appletCardId = appletCardId;
    }

    public String getAppletPayApiKey() {
        return appletPayApiKey;
    }

    public void setAppletPayApiKey(String appletPayApiKey) {
        this.appletPayApiKey = appletPayApiKey;
    }
}
