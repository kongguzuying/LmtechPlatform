package com.lmtech.infrastructure.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.DbEntityBase;

/**
 * 用户角色关联
 * @author huang.jb
 *
 */
@TableName("lm_user_role")
public class UserRole extends DbEntityBase {

	private static final long serialVersionUID = 1L;

	@TableField("USER_ID")
	private String userId;
	@TableField("ROLE_ID")
	private String roleId;
	
	// property
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}
