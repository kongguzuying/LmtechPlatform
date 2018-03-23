package com.lmtech.common;

/**
 * runtime context
 * @author huang.jb
 *
 */
public class Context {
	private String userId;
	private String userName;
	private String accountId;
	private String accountName;
	private String loginName;
	private String groupId;
	private String tenancyId;
	private String tenancyCode;
	private String token;
	private String dataSourceKey;

	// property
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getTenancyId() {
		return tenancyId;
	}
	public void setTenancyId(String tenancyId) {
		this.tenancyId = tenancyId;
	}
	public String getTenancyCode() {
		return tenancyCode;
	}
	public void setTenancyCode(String tenancyCode) {
		this.tenancyCode = tenancyCode;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getDataSourceKey() {
		return dataSourceKey;
	}
	public void setDataSourceKey(String dataSourceKey) {
		this.dataSourceKey = dataSourceKey;
	}
}
