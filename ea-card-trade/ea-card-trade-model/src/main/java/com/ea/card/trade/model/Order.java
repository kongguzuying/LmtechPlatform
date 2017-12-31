package com.ea.card.trade.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.DbEntityBase;
import com.lmtech.model.IdEntity;

@TableName("ea_order")
public class Order extends DbEntityBase {
    @TableField("user_id")
    private String userId;
    @TableField("open_id")
    private String openId;
    @TableField("phone")
    private String phone;
    @TableField("pro_id")
    private String proId;
    @TableField("prod_name")
    private String prodName;
    @TableField("mobile")
    private String mobile;
    @TableField("total_amount")
    private String totalAmount;
    @TableField("type")
    private String type;
    @TableField("entry")
    private String entry;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }
}
