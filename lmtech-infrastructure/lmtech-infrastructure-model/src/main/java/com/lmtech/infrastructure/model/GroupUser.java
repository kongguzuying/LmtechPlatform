package com.lmtech.infrastructure.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.DbEntityBase;

/**
 * 群组用户信息
 * @author huang.jb
 *
 */
@TableName("lm_group_user")
public class GroupUser extends DbEntityBase {

	private static final long serialVersionUID = 1L;
	
	@TableField("GROUP_ID")
	private String groupId;
	@TableField("USER_ID")
	private String userId;
	@TableField("IS_DEFAULT")
	private boolean isDefault;
	
	// property
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public boolean isDefault() {
		return isDefault;
	}
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
}
