package com.ea.card.crm.service.vo;

import java.util.Map;
/**
 * 提交订单
 * @author huacheng.li
 *
 */
public class SubmitPreOrderData {

	private Map<String, Object> preordersinfo;
	
	private String receiverinfoid;
	
	private int receipt;
	
	private String expecttime;
	
	private String openid;
	
	private int paytype;
	
	private String coupon;
	
	private int settletype;
	
	private String grouponid;
	
	private int source;
	
	private String token;

	public Map<String, Object> getPreordersinfo() {
		return preordersinfo;
	}

	public void setPreordersinfo(Map<String, Object> preordersinfo) {
		this.preordersinfo = preordersinfo;
	}

	public String getReceiverinfoid() {
		return receiverinfoid;
	}

	public void setReceiverinfoid(String receiverinfoid) {
		this.receiverinfoid = receiverinfoid;
	}

	public int getReceipt() {
		return receipt;
	}

	public void setReceipt(int receipt) {
		this.receipt = receipt;
	}

	public String getExpecttime() {
		return expecttime;
	}

	public void setExpecttime(String expecttime) {
		this.expecttime = expecttime;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public int getPaytype() {
		return paytype;
	}

	public void setPaytype(int paytype) {
		this.paytype = paytype;
	}

	public String getCoupon() {
		return coupon;
	}

	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}

	public int getSettletype() {
		return settletype;
	}

	public void setSettletype(int settletype) {
		this.settletype = settletype;
	}

	public String getGrouponid() {
		return grouponid;
	}

	public void setGrouponid(String grouponid) {
		this.grouponid = grouponid;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
	
}
