package com.ea.card.crm.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.DbEntityBase;

@TableName("ea_exchange_integral_record")
public class ExchangeIntegralRecord extends DbEntityBase {
	
	@TableField("tid")
	private String tid;
	
	@TableField("user_id")
	private String userId;
	
	@TableField("order_no")
	private String orderNo;
	
	@TableField("product_ids")
	private String productIds;
	
	@TableField("exchange_points")
	private long exchangePoints;
	
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

	public String getProductIds() {
		return productIds;
	}

	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}

	public long getExchangePoints() {
		return exchangePoints;
	}

	public void setExchangePoints(long exchangePoints) {
		this.exchangePoints = exchangePoints;
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
