package com.ea.card.crm.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.DbEntityBase;

/**
 * 积分兑换流水
 * @author huacheng.li
 *
 */

@SuppressWarnings("serial")
@TableName("ea_integral_trading_record")
public class IntegralTradingRecord extends DbEntityBase {
	
	@TableField("tid")
	private String tid;
	
	@TableField("user_id")
	private String userId;
	
	@TableField("order_no")
	private String orderNo;
	
	@TableField("before_consumption_points")
	private long beforeConsumptionPoints;
	
	@TableField("consumption_points")
	private long consumptionPoints;
	
	@TableField("remaining_points")
	private long remainingPoints;
	
	@TableField("status")
	private int status;
	
	@TableField("remark_msg")
	private String remarkMsg;

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public long getBeforeConsumptionPoints() {
		return beforeConsumptionPoints;
	}

	public void setBeforeConsumptionPoints(long beforeConsumptionPoints) {
		this.beforeConsumptionPoints = beforeConsumptionPoints;
	}

	public long getConsumptionPoints() {
		return consumptionPoints;
	}

	public void setConsumptionPoints(long consumptionPoints) {
		this.consumptionPoints = consumptionPoints;
	}

	public long getRemainingPoints() {
		return remainingPoints;
	}

	public void setRemainingPoints(long remainingPoints) {
		this.remainingPoints = remainingPoints;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemarkMsg() {
		return remarkMsg;
	}

	public void setRemarkMsg(String remarkMsg) {
		this.remarkMsg = remarkMsg;
	}
	
	
}
