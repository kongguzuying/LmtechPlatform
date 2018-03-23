package com.ea.card.crm.facade.request;


public class ExchangeIntegralRequest {

	private String userId;
	
	private String orderSn;
	
	private String productIds;
	
	private long integralNum;
	
	private int type;
	
	private String shopName;
	
	private String payChannel;
	
	private String paytime;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getProductIds() {
		return productIds;
	}

	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}

	public long getIntegralNum() {
		return integralNum;
	}

	public void setIntegralNum(long integralNum) {
		this.integralNum = integralNum;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public String getPaytime() {
		return paytime;
	}

	public void setPaytime(String paytime) {
		this.paytime = paytime;
	}
	
	
	
}
