package com.ea.card.crm.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.DbEntityBase;

/**
 * 积分天数值设置
 */
@TableName("ea_integral_set")
public class IntegralSet extends DbEntityBase {
    @TableField("day_no")
    private int dayNo;//第几天
    @TableField("BONUS")
    private int bonus;//积分
    @TableField("bonus_type")
    private int bonusType;//积分类型，0-签到，1-分享，2-送礼物
    @TableField("day_max")
    private int dayMax;//日最大签到，分享次数
    @TableField("main_title")
    private String mainTitle;//主标题
    @TableField("sub_title")
    private String subTitle;//副标题
    @TableField("end_title")
    private String endTitle;//完成时的标题
    @TableField("order_no")
    private int orderNo;//排序

    @TableField(exist = false)
    private int dayCount;//日完成次数
    @TableField(exist = false)
    private int isSign;//当日是否完成，0-未，1-已
    @TableField(exist = false)
    private int signCount;//连续签到天数

    public int getDayNo() {
        return dayNo;
    }

    public void setDayNo(int dayNo) {
        this.dayNo = dayNo;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public int getBonusType() {
        return bonusType;
    }

    public void setBonusType(int bonusType) {
        this.bonusType = bonusType;
    }

    public int getDayMax() {
        return dayMax;
    }

    public void setDayMax(int dayMax) {
        this.dayMax = dayMax;
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getEndTitle() {
        return endTitle;
    }

    public void setEndTitle(String endTitle) {
        this.endTitle = endTitle;
    }

    public int getDayCount() {
        return dayCount;
    }

    public void setDayCount(int dayCount) {
        this.dayCount = dayCount;
    }

    public int getIsSign() {
        return isSign;
    }

    public void setIsSign(int isSign) {
        this.isSign = isSign;
    }

    public int getSignCount() {
        return signCount;
    }

    public void setSignCount(int signCount) {
        this.signCount = signCount;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }
}
