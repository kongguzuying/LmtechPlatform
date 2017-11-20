package com.ea.card.crm.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lmtech.model.DbEntityBase;

import java.util.Date;


@TableName("ea_integral_rule")
public class IntegralRule extends DbEntityBase {
    public static final int STATUS_ONE = 1;//未执行
    public static final int STATUS_TWO = 2;//执行中
    public static final int STATUS_THREE = 3;//已取消
    public static final int STATUS_FOUR = 4;//已结束

    @TableField("name")
    private String name;//活动名称
    @TableField("type")
    private int type;//类型
    @TableField("merber_type")
    private int merberType;//会员类型
    @TableField("rule")
    private int rule;//积分设置
    @TableField("remark")
    private String remark;//备注
    @TableField("status")
    private int status;//执行状态
    @TableField("start_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    protected Date startDate;//开始时间
    @TableField("end_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date endDate;//结束时间

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMerberType() {
        return merberType;
    }

    public void setMerberType(int merberType) {
        this.merberType = merberType;
    }

    public int getRule() {
        return rule;
    }

    public void setRule(int rule) {
        this.rule = rule;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
