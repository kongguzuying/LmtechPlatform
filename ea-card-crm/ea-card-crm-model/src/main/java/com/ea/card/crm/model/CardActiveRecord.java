package com.ea.card.crm.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.DbEntityBase;

import java.util.Date;

/**
 * 会员卡激活记录
 * @author
 */
@TableName("ea_card_active_record")
public class CardActiveRecord extends DbEntityBase {

    /** 领取中 **/
    public static final int STATUS_APPLYING = 1;
    /** 已领取 **/
    public static final int STATUS_APPLIED = 2;
    /** 已激活 **/
    public static final int STATUS_ACTIVE = 3;
    /** 领取失败 **/
    public static final int STATUS_APPLYERR = 4;
    /** 激活失败 **/
    public static final int STATUS_ACTIVEERR = 5;

    @TableField("tid")
    private String tid;             //流水号
    @TableField("user_id")
    private String userId;          //用户ID
    @TableField("user_type")
    private String userType;        //用户类型
    @TableField("app_type")
    private int appType;            //app类型
    @TableField("phone")
    private String phone;           //手机号
    @TableField("open_id")
    private String openId;          //微信openid
    @TableField("friend_open_id")
    private String friendOpenId;    //赠送方微信openid
    @TableField("unique_id")
    private String uniqueId;        //微信uniqueId
    @TableField("nickname")
    private String nickname;        //微信昵称
    @TableField("headimgurl")
    private String headimgurl;      //微信头像
    @TableField("sex")
    private int sex;                //姓别
    @TableField("card_id")
    private String cardId;          //会员卡id
    @TableField("code")
    private String code;            //会员编号
    @TableField("membership_num")
    private String membershipNum;   //会员卡号
    @TableField("balance")
    private double balance;         //余额
    @TableField("bonus")
    private double bonus;           //积分
    @TableField("status")
    private int status;             //0-待申领，1-已申领，2-已激活
    @TableField("refresh_token")
    private String refreshToken;    //微信用户信息refreshToken
    @TableField("wap_token")
    private String wapToken;        //门户token
    @TableField("err_msg")
    private String errMsg;          //激活失败信息
    @TableField("outstr")
    private String outstr;			//微信返回outstr
    
    @TableField(exist = false)
    private Date endDate;           //结束日期
    @TableField(exist = false)
    private int cardLevel;          //卡片等级
    @TableField(exist = false)
    private String cardBackgorund;	//卡背景图片

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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getAppType() {
        return appType;
    }

    public void setAppType(int appType) {
        this.appType = appType;
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

    public String getFriendOpenId() {
        return friendOpenId;
    }

    public void setFriendOpenId(String friendOpenId) {
        this.friendOpenId = friendOpenId;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMembershipNum() {
        return membershipNum;
    }

    public void setMembershipNum(String membershipNum) {
        this.membershipNum = membershipNum;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getWapToken() {
        return wapToken;
    }

    public void setWapToken(String wapToken) {
        this.wapToken = wapToken;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getCardLevel() {
        return cardLevel;
    }

    public void setCardLevel(int cardLevel) {
        this.cardLevel = cardLevel;
    }

    public String getOutstr() {
        return outstr;
    }

    public void setOutstr(String outstr) {
        this.outstr = outstr;
    }

	public String getCardBackgorund() {
		return cardBackgorund;
	}

	public void setCardBackgorund(String cardBackgorund) {
		this.cardBackgorund = cardBackgorund;
	}
}
