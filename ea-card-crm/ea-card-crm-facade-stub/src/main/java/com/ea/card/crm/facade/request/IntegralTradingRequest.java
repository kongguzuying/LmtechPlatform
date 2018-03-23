package com.ea.card.crm.facade.request;

import java.util.List;
import java.util.Map;

public class IntegralTradingRequest {
	
	//用户ID
	private String userId;
	
	//收货地址
	private String receiverinfoId;
	
	//流水号
	private String tId;
	
	//消费积分
	private long consumptionIntegral;
	
	//微信openId
	private String openId;

	//门户登录token
	private String token;
	
	//商品列表
	private List<Map<String,String>> goodslist;
	
	//商品数量
	private int amount;

	public String getUserId() {
		return userId;
	}

	public String getReceiverinfoId() {
		return receiverinfoId;
	}

	public void setReceiverinfoId(String receiverinfoId) {
		this.receiverinfoId = receiverinfoId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String gettId() {
		return tId;
	}

	public void settId(String tId) {
		this.tId = tId;
	}

	public long getConsumptionIntegral() {
		return consumptionIntegral;
	}

	public void setConsumptionIntegral(long consumptionIntegral) {
		this.consumptionIntegral = consumptionIntegral;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<Map<String, String>> getGoodslist() {
		return goodslist;
	}

	public void setGoodslist(List<Map<String, String>> goodslist) {
		this.goodslist = goodslist;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	
}
