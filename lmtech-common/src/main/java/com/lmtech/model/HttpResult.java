package com.lmtech.model;

public class HttpResult extends EntityBase {

	private static final long serialVersionUID = 1L;

	private String content;
	private int statusCode;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
}
