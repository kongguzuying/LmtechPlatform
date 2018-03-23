package com.ea.card.crm.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.DbEntityBase;

import java.util.Date;

/**
 * 礼品会员卡
 * @author Administrator
 */
@TableName("ea_gift_member_card")
public class GiftMemberCard extends DbEntityBase {

    /** 新建状态 **/
    public static final int STATUS_NEW = 1;
    /** 领取锁定状态 **/
    public static final int STATUS_RECVLOCK = 2;
    /** 已被领取状态 **/
    public static final int STATUS_RECEIVED = 3;
    /** 已被激活状态 **/
    public static final int STATUS_ACTIVED = 4;
    /** 不可用状态 **/
    public static final int STATUS_DISABLE = 5;

    /** 内部赠送 **/
    public static final int SOURCE_INNER_PRESENT = 1;
    /** 购买 **/
    public static final int SOURCE_PAY = 2;

    /** 普通会员 **/
    public static final int LEVEL_COMMON = 1;
    /** VPass **/
    public static final int LEVEL_VPASS = 2;

    @TableField("card_level")
    private int cardLevel;      //卡会员等级
    @TableField("card_title")
    private String cardTitle;   //卡片标题
    @TableField("card_background")
    private String cardBackground;//卡片背景
    @TableField("card_category_id")
    private String cardCategoryId;//卡片分类id
    @TableField("gift_category_id")
    private String giftCategoryId;//礼品分类id
    @TableField("valid_month")
    private int validMonth;     //卡片有效月数
    @TableField("source")
    private int source;         //卡片来源
    @TableField("balance")
    private double balance;     //卡片金额
    @TableField("order_no")
    private String orderNo;     //订单编号
    @TableField("user_id")
    private String userId;      //用户ID（生活）
    @TableField("phone")
    private String phone;       //用户手机号
    @TableField("open_id")
    private String openId;      //用户OpenId
    @TableField("status")
    private int status;         //状态
    @TableField("recv_user_id")
    private String recvUserId;  //接收人用户ID（生活）
    @TableField("recv_phone")
    private String recvPhone;   //接收人手机号
    @TableField("recv_open_id")
    private String recvOpenId;  //接收人OpenId
    @TableField("recv_date")
    private Date recvDate;      //领取时间
    @TableField("active_date")
    private Date activeDate;    //激活时间

    @TableField(exist = false)
    private String cardLevelName;//卡片等级
    @TableField(exist = false)
    private String recvNickname;//领卡人昵称
    @TableField(exist = false)
    private String recvHeadimgurl;//领卡人头像

    /**
     * 是否待赠送
     * @return
     */
    public boolean isWaitPresent() {
        return status == STATUS_NEW;
    }

    public int getCardLevel() {
        return cardLevel;
    }

    public void setCardLevel(int cardLevel) {
        this.cardLevel = cardLevel;
    }

    public String getCardTitle() {
        return cardTitle;
    }

    public void setCardTitle(String cardTitle) {
        this.cardTitle = cardTitle;
    }

    public String getCardBackground() {
        return cardBackground;
    }

    public void setCardBackground(String cardBackground) {
        this.cardBackground = cardBackground;
    }

    public String getCardCategoryId() {
        return cardCategoryId;
    }

    public void setCardCategoryId(String cardCategoryId) {
        this.cardCategoryId = cardCategoryId;
    }

    public String getGiftCategoryId() {
        return giftCategoryId;
    }

    public void setGiftCategoryId(String giftCategoryId) {
        this.giftCategoryId = giftCategoryId;
    }

    public int getValidMonth() {
        return validMonth;
    }

    public void setValidMonth(int validMonth) {
        this.validMonth = validMonth;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
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

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRecvUserId() {
        return recvUserId;
    }

    public void setRecvUserId(String recvUserId) {
        this.recvUserId = recvUserId;
    }

    public String getRecvPhone() {
        return recvPhone;
    }

    public void setRecvPhone(String recvPhone) {
        this.recvPhone = recvPhone;
    }

    public String getRecvOpenId() {
        return recvOpenId;
    }

    public void setRecvOpenId(String recvOpenId) {
        this.recvOpenId = recvOpenId;
    }

    public Date getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(Date activeDate) {
        this.activeDate = activeDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getRecvDate() {
        return recvDate;
    }

    public void setRecvDate(Date recvDate) {
        this.recvDate = recvDate;
    }

    public String getCardLevelName() {
        return cardLevelName;
    }

    public void setCardLevelName(String cardLevelName) {
        this.cardLevelName = cardLevelName;
    }

    public String getRecvNickname() {
        return recvNickname;
    }

    public void setRecvNickname(String recvNickname) {
        this.recvNickname = recvNickname;
    }

    public String getRecvHeadimgurl() {
        return recvHeadimgurl;
    }

    public void setRecvHeadimgurl(String recvHeadimgurl) {
        this.recvHeadimgurl = recvHeadimgurl;
    }
}
