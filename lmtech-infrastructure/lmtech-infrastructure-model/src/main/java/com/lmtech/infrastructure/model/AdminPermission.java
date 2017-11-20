package com.lmtech.infrastructure.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.DbEntityBase;

/**
 * 管理员资源权限
 * @author huang.jb
 *
 */
@TableName("lm_admin_permission")
public class AdminPermission extends DbEntityBase {

	private static final long serialVersionUID = 1L;

	/** 资源类型-系统参数 */
	public static final String RESOURCE_TYPE_SYSTEMCONFIG = "SystemConfig";
	/** 资源类型-代码表 */
	public static final String RESOURCE_TYPE_CODETYPE = "CodeType";
	/** 资源类型-菜单 */
	public static final String RESOURCE_TYPE_MENU = "Menu";
	/** 资源类型-角色 */
	public static final String RESOURCE_TYPE_ROLE = "Role";
	/** 资源类型-用户 */
	public static final String RESOURCE_TYPE_USER = "User";
	
	@TableField("RESOURCE_ID")
	private String resourceId;
	@TableField("RESOURCE_TYPE")
	private String resourceType;
	
	//getter and setter
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
}
