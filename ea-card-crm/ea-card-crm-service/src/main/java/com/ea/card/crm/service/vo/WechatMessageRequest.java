package com.ea.card.crm.service.vo;

public class WechatMessageRequest {
	
	//流水号ID
	private String tId;
	
	//消息请求来源
	private String sys;
	
	//用户类型，1=买家，2=卖家，3=其他
	private String userType;
	
	//消息模板ID，微信提供
	private String templateId;
	
	//消息内容,JSON 类型的字符串
	private String msgContent;
	
	//接收消息用户的身份标识
	private String openId;
	
	//微信消息的跳转链接
	private String url;
	
	//微信公众号类型：2=游物欧品
	private String weChatType;

	public String gettId() {
		return tId;
	}

	public void settId(String tId) {
		this.tId = tId;
	}

	public String getSys() {
		return sys;
	}

	public void setSys(String sys) {
		this.sys = sys;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getWeChatType() {
		return weChatType;
	}

	public void setWeChatType(String weChatType) {
		this.weChatType = weChatType;
	}
	
	
}
