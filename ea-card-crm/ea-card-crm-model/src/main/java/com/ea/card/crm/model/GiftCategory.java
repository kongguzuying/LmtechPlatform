package com.ea.card.crm.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.DbEntityBase;

/**
 * 礼品分类
 * @author
 */
@TableName("ea_gift_category")
public class GiftCategory extends DbEntityBase {
    @TableField("title")
    private String title;
    @TableField("price")
    private double price;
    @TableField("price_title")
    private String priceTitle;
    @TableField("sort_no")
    private String sortNo;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPriceTitle() {
        return priceTitle;
    }

    public void setPriceTitle(String priceTitle) {
        this.priceTitle = priceTitle;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSortNo() {
        return sortNo;
    }

    public void setSortNo(String sortNo) {
        this.sortNo = sortNo;
    }
}
