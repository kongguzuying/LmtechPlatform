package com.lmtech.infrastructure.service;

import java.util.List;
import java.util.Map;

import com.lmtech.common.PageData;
import com.lmtech.infrastructure.model.Menu;
import com.lmtech.infrastructure.model.Role;
import com.lmtech.service.DbManagerBase;
import com.lmtech.service.NotAdminDbManager;

public interface RoleManager extends DbManagerBase<Role>, NotAdminDbManager {
	/**
	 * 获取角色相关用户
	 * @param roleId
	 * @param args
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageData getRoleUsers(String roleId, Map<String, Object> args, int pageIndex, int pageSize);
	/**
	 * 获取角色相关用户，非系统管理员
	 * @param roleId
	 * @param args
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageData getRoleUsersNotAdmin(String roleId, Map<String, Object> args, int pageIndex, int pageSize);
	/**
	 * 获取角色未相关用户
	 * @param roleId
	 * @param args
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageData getRoleUnauthUsers(String roleId, Map<String, Object> args, int pageIndex, int pageSize);
	/**
	 * 获取角色未相关用户，非系统管理员
	 * @param roleId
	 * @param args
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageData getRoleUnauthUsersNotAdmin(String roleId, Map<String, Object> args, int pageIndex, int pageSize);
	/**
	 * 设置角色相关用户
	 * @param userIds
	 */
	void setRoleUsers(String roleId, List<String> userIds);
	/**
	 * 添加角色相关用户
	 * @param roleId
	 * @param userIds
	 */
	void addRoleUsers(String roleId, List<String> userIds);
	/**
	 * 删除角色相关用户
	 * @param roleId
	 * @param userIds
	 */
	void removeRoleUsers(String roleId, List<String> userIds);
	/**
	 * 获取角色相关Action
	 * @param roleId
	 * @param args
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageData getRoleActions(String roleId, Map<String, Object> args, int pageIndex, int pageSize);
	/**
	 * 获取角色未授权Action
	 * @param roleId
	 * @param args
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageData getRoleUnauthActions(String roleId, Map<String, Object> args, int pageIndex, int pageSize);
	/**
	 * 设置角色相关Action
	 * @param roleId
	 * @param actionIds
	 */
	void setRoleActions(String roleId, List<String> actionIds);
	/**
	 * 添加角色相关Action
	 * @param roleId
	 * @param actionIds
	 */
	void addRoleActions(String roleId, List<String> actionIds);
	/**
	 * 删除角色相关Action
	 * @param roleId
	 * @param actionIds
	 */
	void removeRoleActions(String roleId, List<String> actionIds);
	/**
	 * 获取角色未授权菜单
	 * @param roleId
	 * @return
	 */
	List<Menu> getRoleUnauthMenus(String roleId);
	/**
	 * 获取角色相关菜单
	 * @param roleId
	 * @return
	 */
	List<Menu> getRoleMenus(String roleId);
	/**
	 * 设置角色相关菜单
	 * @param roleId
	 * @param menuIds
	 */
	void setRoleMenus(String roleId, List<String> menuIds);
	/**
	 * 添加角色相关菜单
	 * @param roleId
	 * @param menuIds
	 */
	void addRoleMenus(String roleId, List<String> menuIds);
	/**
	 * 删除角色相关菜单
	 * @param roleId
	 * @param menuIds
	 */
	void removeRoleMenus(String roleId, List<String> menuIds);
	/**
	 * 获取角色相关资源
	 * @param roleId
	 * @param args
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageData getRoleResources(String roleId, Map<String, Object> args, int pageIndex, int pageSize);
	/**
	 * 获取角色未相关资源
	 * @param roleId
	 * @param args
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageData getRoleUnauthResources(String roleId, Map<String, Object> args, int pageIndex, int pageSize);
	/**
	 * 设置角色相关资源
	 * @param resourceIds
	 */
	void setRoleResources(String roleId, List<String> resourceIds);
	/**
	 * 添加角色相关资源
	 * @param roleId
	 * @param resourceIds
	 */
	void addRoleResources(String roleId, List<String> resourceIds);
	/**
	 * 删除角色相关资源
	 * @param roleId
	 * @param resourceIds
	 */
	void removeRoleResources(String roleId, List<String> resourceIds);
}
