package com.lmtech.infrastructure.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.DbEntityBase;

@TableName("lm_role_action_permission")
public class RoleActionPermission extends DbEntityBase {

	private static final long serialVersionUID = 1L;
	
	@TableField("ROLE_ID")
	private String roleId;
	@TableField("ACTION_ID")
	private String actionId;
	
	// property
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getActionId() {
		return actionId;
	}
	public void setActionId(String actionId) {
		this.actionId = actionId;
	}
}
