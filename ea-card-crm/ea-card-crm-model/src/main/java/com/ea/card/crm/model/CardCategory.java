package com.ea.card.crm.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.common.ComplexBase;
import com.lmtech.model.DbEntityBase;

import java.util.ArrayList;
import java.util.List;

@TableName("ea_card_category")
public class CardCategory extends ComplexBase<CardCategory> {
    private static final long serialVersionUID = 1L;

    @TableField("background")
    private String background;      //卡面背景
    @TableField("wx_background")
    private String wx_background;      //微信卡面背景
    @TableField("title")
    private String title;           //标题
    @TableField("remark")
    private String remark;//备注
    @TableField("category")
    private String category;          //类型
    @TableField("sort_no")
    private String sortNo;

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSortNo() {
        return sortNo;
    }

    public void setSortNo(String sortNo) {
        this.sortNo = sortNo;
    }

    public String getWx_background() {
        return wx_background;
    }

    public void setWx_background(String wx_background) {
        this.wx_background = wx_background;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
