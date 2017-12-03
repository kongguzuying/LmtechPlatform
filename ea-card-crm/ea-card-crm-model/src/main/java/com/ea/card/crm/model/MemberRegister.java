package com.ea.card.crm.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.DbEntityBase;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

/**
 * 会员注册信息
 * @author
 */
@TableName("ea_member_register")
public class MemberRegister extends DbEntityBase {

    public static final String USER_TYPE_INNER = "0";       //内部用户
    public static final String USER_TYPE_OUTER = "1";       //外部用户

    public static final int MLEVEL_NORMAL = 1;      //普通会员
    public static final int MLEVEL_VPASS = 2;       //VPass会员

    public static final int DELETE_YES = 1;      //逻辑删除
    public static final int DELETE_NO = 0;       //正常

    public static final int APP_TYPE_H5 = 1;
    public static final int APP_TYPE_APPLET = 2;
    
    

    @TableField("user_id")
    private String userId;          //用户id
    @TableField("user_type")
    private String userType;        //用户类型（内/外）部
    @TableField("app_type")
    private int appType;            //app类型
    @TableField("phone")
    private String phone;           //手机号
    @TableField("open_id")
    private String openId;          //微信号id
    @TableField("unique_id")
    private String uniqueId;        //微信号唯一id
    @TableField("official_open_id")
    private String officialOpenId;  //公众号微信openid
    @TableField("nickname")
    private String nickname;        //微信昵称
    @TableField("headimgurl")
    private String headimgurl;      //微信头像
    @TableField("sex")
    private int sex;                //姓别
    @TableField("card_id")
    private String cardId;          //会员卡id
    @TableField("card_background")
    private String cardBackground;  //卡片背景
    @TableField("code")
    private String code;            //会员编号
    @TableField("membership_num")
    private String membershipNum;   //会员卡号
    @TableField("mlevel")
    private int mLevel;             //会员等级
    @TableField("begin_date")
    private Date beginDate;         //起始时间
    @TableField("end_date")
    private Date endDate;           //结束时间
    @TableField("forever")
    private boolean forever;        //是否永久等级
    @TableField("trial")
    private boolean trial;          //是否试用
    @TableField("trial_over")
    private boolean trialOver;      //是否试用结束
    @TableField("trial_date")
    private Date trialDate;         //试用日期
    @TableField("trial_day")
    private int trialDay;           //试用天数
    @TableField("auth_refresh_token")
    private String authRefreshToken;//授权RefreshToken
    @TableField("integral")
    private long integral;        //积分

    @TableField(exist = false)
    private double overTimeDays;    //
    @TableField(exist = false)
    private String token;           //token

    /**
     * 是否试用中
     * @return
     */
    public boolean isTrialing() {
        if (this.isTrial() && !this.isTrialOver()) {
            return true;
        } else {
            return false;
        }
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

    public String getOfficialOpenId() {
        return officialOpenId;
    }

    public void setOfficialOpenId(String officialOpenId) {
        this.officialOpenId = officialOpenId;
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

    public String getCardBackground() {
        return cardBackground;
    }

    public void setCardBackground(String cardBackground) {
        this.cardBackground = cardBackground;
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

    public int getmLevel() {
        return mLevel;
    }

    public void setmLevel(int mLevel) {
        this.mLevel = mLevel;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isForever() {
        return forever;
    }

    public void setForever(boolean forever) {
        this.forever = forever;
    }

    public boolean isTrial() {
        return trial;
    }

    public void setTrial(boolean trial) {
        this.trial = trial;
    }

    public boolean isTrialOver() {
        return trialOver;
    }

    public void setTrialOver(boolean trialOver) {
        this.trialOver = trialOver;
    }

    public Date getTrialDate() {
        return trialDate;
    }

    public void setTrialDate(Date trialDate) {
        this.trialDate = trialDate;
    }

    public int getTrialDay() {
        return trialDay;
    }

    public void setTrialDay(int trialDay) {
        this.trialDay = trialDay;
    }

    public String getAuthRefreshToken() {
        return authRefreshToken;
    }

    public void setAuthRefreshToken(String authRefreshToken) {
        this.authRefreshToken = authRefreshToken;
    }

    public double getOverTimeDays() {
        return overTimeDays;
    }

    public void setOverTimeDays(double overTimeDays) {
        this.overTimeDays = overTimeDays;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getIntegral() {
        return integral;
    }

    public void setIntegral(long integral) {
        this.integral = integral;
    }
}
