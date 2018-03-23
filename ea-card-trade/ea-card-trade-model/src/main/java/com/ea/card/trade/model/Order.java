package com.ea.card.trade.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.DbEntityBase;
import com.lmtech.model.IdEntity;

@TableName("ea_order")
public class Order extends DbEntityBase {
    /** 待支付 */
    public static final int STATUS_PAY_WAIT = 1;
    /** 支付中 */
    public static final int STATUS_PAY_ING = 2;
    /** 支付成功 */
    public static final int STATUS_PAY_SUCCESS = 3;
    /** 支付失败 */
    public static final int STATUS_PAY_FAILED = 4;

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
    private double totalAmount;
    @TableField("type")
    private int type;
    @TableField("entry")
    private int entry;
    @TableField("status")
    private int status;

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

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getEntry() {
        return entry;
    }

    public void setEntry(int entry) {
        this.entry = entry;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
