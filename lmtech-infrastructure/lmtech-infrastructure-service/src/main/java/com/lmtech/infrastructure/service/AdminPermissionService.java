package com.lmtech.infrastructure.service;

import java.util.List;

import com.lmtech.infrastructure.model.AdminPermission;
import com.lmtech.service.DbServiceBase;

/**
 * 管理员资源权限服务
 * @author huang.jb
 *
 */
public interface AdminPermissionService extends DbServiceBase<AdminPermission> {
	/**
	 * 获取管理员系统参数配置资源ID列表
	 * @return
	 */
	List<String> getAdminSystemConfigs();
	/**
	 * 获取管理员代码表配置资源ID列表
	 * @return
	 */
	List<String> getAdminCodeTypes();
	/**
	 * 获取管理员菜单资源ID列表
	 * @return
	 */
	List<String> getAdminMenus();
	/**
	 * 获取管理员角色资源ID列表
	 * @return
	 */
	List<String> getAdminRoles();
	/**
	 * 获取管理员用户资源ID列表
	 * @return
	 */
	List<String> getAdminUsers();
}
