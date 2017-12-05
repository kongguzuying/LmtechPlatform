package com.lmtech.infrastructure.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.lmtech.model.DbEntityBase;

/**
 * 租户
 *
 * @author huang.jb
 */
public class Store extends DbEntityBase {
    @TableField("code")
    private String code;           //租户编码

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
}
