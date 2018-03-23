package com.ea.card.crm.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.DbEntityBase;

/**
 * 充值交易记录
 * @author
 */
@TableName("ea_recharge_pay_record")
public class RechargePayRecord extends DbEntityBase {

    public static final String CONTEXT_KEY = "CONTEXT_RECHARGE_PAY";

    /** 待支付 **/
    public static final int STATUS_WAIT_PAY = 1;
    /** 已支付 **/
    public static final int STATUS_PAYED = 2;
    /** 已核销完成 **/
    public static final int STATUS_FINISHED = 3;
    /** 操作出错 **/
    public static final int STATUS_ERROR = 5;

    /** 余额支付 **/
    public static final int PAY_TYPE_BALANCE = 1;
    /** 微信支付 **/
    public static final int PAY_TYPE_WX = 1;

    @TableField("tid")
    private String tid;             //流水号
    @TableField("user_id")
    private String userId;          //用户编号
    @TableField("phone")
    private String phone;           //手机号
    @TableField("pro_id")
    private String proId;           //产品id
    @TableField("total_amount")
    private double totalAmount;     //金额
    @TableField("type")
    private int type;               //第三方服务类型
    @TableField("entry")
    private int entry;              //入口类型
    @TableField("order_no")
    private String orderNo;         //订单编号
    @TableField("paychannel")
    private int paychannel;         //支付渠道
    @TableField("pay_type")
    private int payType;            //支付类型
    @TableField("status")
    private int status;             //状态
    @TableField("err_msg")
    private String errMsg;          //错误消息

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getPaychannel() {
        return paychannel;
    }

    public void setPaychannel(int paychannel) {
        this.paychannel = paychannel;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }
}
