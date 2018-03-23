package com.lmtech.infrastructure.service;

import java.util.List;

import com.lmtech.infrastructure.model.Group;
import com.lmtech.infrastructure.model.Role;
import com.lmtech.infrastructure.model.User;
import com.lmtech.infrastructure.vo.UserInfo;
import com.lmtech.service.DbServiceBase;

/**
 * 用户服务
 * @author huang.jb
 *
 */
public interface UserService extends DbServiceBase<User> {
	/**
	 * 获取用户相关角色
	 * @param userId
	 * @return
	 */
	List<Role> getUserRoles(String userId);

	/**
	 * 获取用户相关群组
	 * @param userId
	 * @return
	 */
	List<Group> getUserGroups(String userId);

	/**
	 * 是否系统管理员
	 * @param userId
	 * @return
	 */
	boolean isAdmin(String userId);

	/**
	 * 是否超级管理员
	 * @param userId
	 * @return
     */
	boolean isSuperAdmin(String userId);

	/**
	 * 通过帐户获取用户信息
	 * @param account
	 * @return
     */
	UserInfo getUserInfoByAccount(String account);

	/**
	 * 通过关键字获取用户数据，如姓名登录名等
	 * @param key
	 * @return
     */
	List<User> getUserByKey(String key);
}
