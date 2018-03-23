package com.ea.card.crm.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.DbEntityBase;

import java.util.Date;

/**
 * 卡片赠送记录
 * @author
 */
@TableName("ea_card_present_record")
public class CardPresentRecord extends DbEntityBase {

    public static final int STATUS_NEW = 1;
    public static final int STATUS_FINISH = 2;
    public static final int STATUS_OVERTIME = 3;

    @TableField("total_amount")
    private double totalAmount;         //总金额
    @TableField("total_number")
    private int totalNumber;            //总张数
    @TableField("card_level")
    private int cardLevel;              //卡片等级
    @TableField("card_level_name")
    private String cardLevelName;       //卡片等级名称
    @TableField("user_id")
    private String userId;              //赠卡人用户id
    @TableField("over_time_number")
    private int overTimeNumber;         //超时卡片张数
    @TableField("receive_number")
    private int receiveNumber;          //已领取卡片张数
    @TableField("gift_card_ids")
    private String giftCardIds;         //礼品卡片id列表
    @TableField("over_time_card_ids")
    private String overTimeCardIds;     //超时礼品卡片id列表
    @TableField("present_date")
    private Date presentDate;           //赠送日期
    @TableField("status")
    private int status;                 //赠送状态
    @TableField("order_no")
    private String orderNo;             //订单编号

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public int getCardLevel() {
        return cardLevel;
    }

    public void setCardLevel(int cardLevel) {
        this.cardLevel = cardLevel;
    }

    public String getCardLevelName() {
        return cardLevelName;
    }

    public void setCardLevelName(String cardLevelName) {
        this.cardLevelName = cardLevelName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getOverTimeNumber() {
        return overTimeNumber;
    }

    public void setOverTimeNumber(int overTimeNumber) {
        this.overTimeNumber = overTimeNumber;
    }

    public int getReceiveNumber() {
        return receiveNumber;
    }

    public void setReceiveNumber(int receiveNumber) {
        this.receiveNumber = receiveNumber;
    }

    public String getGiftCardIds() {
        return giftCardIds;
    }

    public void setGiftCardIds(String giftCardIds) {
        this.giftCardIds = giftCardIds;
    }

    public Date getPresentDate() {
        return presentDate;
    }

    public void setPresentDate(Date presentDate) {
        this.presentDate = presentDate;
    }

    public String getOverTimeCardIds() {
        return overTimeCardIds;
    }

    public void setOverTimeCardIds(String overTimeCardIds) {
        this.overTimeCardIds = overTimeCardIds;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
