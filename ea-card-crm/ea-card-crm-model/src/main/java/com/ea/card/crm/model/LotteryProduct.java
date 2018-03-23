package com.ea.card.crm.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.DbEntityBase;

/**
 * 积分抽奖商品
 * @author
 */
@TableName("ea_lottery_product")
public class LotteryProduct extends DbEntityBase {

    public static final int TYPE_INTEGRAL = 1;      //积分
    public static final int TYPE_MATTER = 2;        //实物
    public static final int TYPE_CASH = 3;          //现金

    @TableField("name")
    private String name;        //商品名称
    @TableField("image_url")
    private String imageUrl;    //商品图片地址
    @TableField("sort_no")
    private int sortNo;         //排序号
    @TableField("prize")
    private boolean prize;      //是否中奖
    @TableField("join")
    private boolean join;       //是否参与抽奖
    @TableField("type")
    private int type;           //奖品类型
    @TableField("number")
    private double number;      //数值

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }

    public boolean isPrize() {
        return prize;
    }

    public void setPrize(boolean prize) {
        this.prize = prize;
    }

    public boolean isJoin() {
        return join;
    }

    public void setJoin(boolean join) {
        this.join = join;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }
}
