package com.ea.card.crm.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.DbEntityBase;
/**
 * 积分签到日志
 */
@TableName("ea_integral_sign_log")
public class IntegralSignLog extends DbEntityBase {
    @TableField("sign_count")
    private int signCount;//连续签到次数
    @TableField("user_id")
    private String userId;//用户ID
    @TableField("is_sign")
    private int isSign;//当日是否已经签到，0-未，1-已
    @TableField("diff")
    private  int diff;//时间差,更新时间与当前时间的差，等于0，表示当前，等于1表示连续，大于1表示中间断了，没有连续
    @TableField("bonus_type")
    private int bonusType;//积分类型，0-签到，1-分享，2-送礼物
    @TableField("day_count")
    private int dayCount;//日签到次数

    public int getSignCount() {
        return signCount;
    }

    public void setSignCount(int signCount) {
        this.signCount = signCount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getIsSign() {
        return isSign;
    }

    public void setIsSign(int isSign) {
        this.isSign = isSign;
    }

    public int getDiff() {
        return diff;
    }

    public void setDiff(int diff) {
        this.diff = diff;
    }

    public int getBonusType() {
        return bonusType;
    }

    public void setBonusType(int bonusType) {
        this.bonusType = bonusType;
    }

    public int getDayCount() {
        return dayCount;
    }

    public void setDayCount(int dayCount) {
        this.dayCount = dayCount;
    }
}
