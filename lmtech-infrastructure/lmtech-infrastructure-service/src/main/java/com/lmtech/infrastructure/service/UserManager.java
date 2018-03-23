package com.lmtech.infrastructure.service;

import java.util.List;
import java.util.Map;

import com.lmtech.common.PageData;
import com.lmtech.infrastructure.exceptions.UserManagerException;
import com.lmtech.infrastructure.model.User;
import com.lmtech.service.DbManagerBase;
import com.lmtech.service.NotAdminDbManager;

/**
 * 用户管理接口
 * @author huang.jb
 *
 */
public interface UserManager extends DbManagerBase<User>, NotAdminDbManager {
	/**
	 * 获取用户相关角色
	 * @param userId
	 * @param args
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageData getUserRoles(String userId, Map<Object, Object> args, int pageIndex, int pageSize);
	/**
	 * 获取用户相关角色，非管理员帐户
	 * @param userId
	 * @param args
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageData getUserRolesNotAdmin(String userId, Map<Object, Object> args, int pageIndex, int pageSize);
	/**
	 * 获取用户未相关的角色
	 * @param userId
	 * @param args
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageData getUserUnauthRoles(String userId, Map<Object, Object> args, int pageIndex, int pageSize);
	/**
	 * 获取用户未相关的角色，非管理员帐户
	 * @param userId
	 * @param args
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageData getUserUnauthRolesNotAdmin(String userId, Map<Object, Object> args, int pageIndex, int pageSize);
	/**
	 * 设置用户相关角色
	 * @param userId
	 * @param roleIds
	 */
	void setUserRoles(String userId, List<String> roleIds);
	/**
	 * 添加用户相关角色
	 * @param userId
	 * @param roleIds
	 */
	void addUserRoles(String userId, List<String> roleIds);
	/**
	 * 删除用户相关角色
	 * @param userId
	 * @param roleIds
	 */
	void removeUserRoles(String userId, List<String> roleIds);
}
