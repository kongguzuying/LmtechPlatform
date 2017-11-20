package com.ea.card.crm.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.DbEntityBase;

import java.util.Date;

/**
 * 积分抽奖
 * @author
 */
@TableName("ea_integral_lottery")
public class IntegralLottery extends DbEntityBase {
    @TableField("user_id")
    private String userId;      //用户编号
    @TableField("result_id")
    private String resultId;    //中奖结果编号
    @TableField("prize")
    private boolean prize;      //没有中奖
    @TableField("product_ids")
    private String productIds;  //产品id列表
    @TableField("strategy")
    private String strategy;    //抽奖策略

    @TableField(exist = false)
    private String phone;       //手机号
    @TableField(exist = false)
    private Date prizeDate;     //中奖日期
    @TableField(exist = false)
    private String nickname;    //昵称
    @TableField(exist = false)
    private String headimgurl;  //头像
    @TableField(exist = false)
    private String prizeProductName;//中奖商品名称

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public boolean isPrize() {
        return prize;
    }

    public void setPrize(boolean prize) {
        this.prize = prize;
    }

    public String getProductIds() {
        return productIds;
    }

    public void setProductIds(String productIds) {
        this.productIds = productIds;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getPrizeDate() {
        return prizeDate;
    }

    public void setPrizeDate(Date prizeDate) {
        this.prizeDate = prizeDate;
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

    public String getPrizeProductName() {
        return prizeProductName;
    }

    public void setPrizeProductName(String prizeProductName) {
        this.prizeProductName = prizeProductName;
    }
}
