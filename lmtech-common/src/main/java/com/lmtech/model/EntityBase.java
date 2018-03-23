package com.lmtech.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lmtech.util.JsonUtil;

import java.io.Serializable;
import java.util.Date;

public abstract class EntityBase implements Serializable {

	private static final long serialVersionUID = 1L;
	@TableField("create_date")
	private Date createDate;
	@TableField("create_user")
	private String createUser;
	@TableField("create_user_name")
	private String createUserName;
	@TableField("update_date")
	private Date updateDate;
	@TableField("update_user_name")
	private String updateUserName;
	@TableField("update_user")
	private String updateUser;
	@TableField("data_version")
	private int dataVersion;
	@TableField("is_delete")
	private boolean isDelete;

	@TableField(exist = false)
	private boolean increased;

	/**
	 * 拷贝自身
	 * @return
	 */
	public <T> T copy() {
		String json = JsonUtil.toJson(this);
		return (T) JsonUtil.fromJson(json, this.getClass());
	}

	/**
	 * 递增数据版本
	 */
	public void increaseDataVersion() {
		if (!increased) {
			dataVersion++;
			increased = true;
		}
	}

	/**
	 * 是否已递增数据版本
	 * @return
     */
	private boolean hasIncreaseDV() {
		return increased;
	}
	
	// property
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getUpdateUserName() {
		return updateUserName;
	}
	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}
	public int getDataVersion() {
		return dataVersion;
	}
	public void setDataVersion(int dataVersion) {
		this.dataVersion = dataVersion;
	}
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean delete) {
		isDelete = delete;
	}
}
