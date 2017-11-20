package com.lmtech.infrastructure.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.DbEntityBase;

@TableName("lm_role_menu_permission")
public class RoleMenuPermission extends DbEntityBase {

	private static final long serialVersionUID = 1L;
	
	@TableField("ROLE_ID")
	private String roleId;
	@TableField("MENU_ID")
	private String menuId;
	
	// property
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
}
