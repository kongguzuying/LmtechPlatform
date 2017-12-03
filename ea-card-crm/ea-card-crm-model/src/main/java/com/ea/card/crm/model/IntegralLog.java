package com.ea.card.crm.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.DbEntityBase;

/**
 * 积分日志
 * @author
 */
@TableName("ea_integral_log")
public class IntegralLog extends DbEntityBase {
    @TableField("type")
    private int type;       //积分类型
    @TableField("source")
    private int source;     //积分来源
    @TableField("user_id")
    private String userId;  //用户id
    @TableField("order_id")
    private String orderId; //积分消费订单id
    @TableField("integral")
    private long integral;  //积分值

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public long getIntegral() {
        return integral;
    }

    public void setIntegral(long integral) {
        this.integral = integral;
    }
}
