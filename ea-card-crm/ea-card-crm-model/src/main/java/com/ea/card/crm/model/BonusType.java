package com.ea.card.crm.model;

import com.baomidou.mybatisplus.annotations.TableField;

public class BonusType {
    @TableField(exist = false)
    private int bonusType;//积分类型，0-签到，1-分享，2-送礼物
    @TableField(exist = false)
    private int dayMax;//日最大签到，分享次数

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
}
