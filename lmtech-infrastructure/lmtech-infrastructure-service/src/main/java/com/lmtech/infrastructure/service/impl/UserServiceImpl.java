package com.lmtech.infrastructure.service.impl;

import com.lmtech.dao.Dao;
import com.lmtech.infrastructure.constants.SystemConfigConst;
import com.lmtech.infrastructure.dao.GroupDao;
import com.lmtech.infrastructure.dao.RoleDao;
import com.lmtech.infrastructure.dao.UserDao;
import com.lmtech.infrastructure.model.Group;
import com.lmtech.infrastructure.model.Role;
import com.lmtech.infrastructure.model.User;
import com.lmtech.infrastructure.service.RoleService;
import com.lmtech.infrastructure.service.SystemConfigService;
import com.lmtech.infrastructure.service.UserManager;
import com.lmtech.infrastructure.service.UserService;
import com.lmtech.infrastructure.vo.UserInfo;
import com.lmtech.redis.service.RedisDataService;
import com.lmtech.service.support.AbstractDbServiceBaseImpl;
import com.lmtech.util.JsonUtil;
import com.lmtech.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class UserServiceImpl extends AbstractDbServiceBaseImpl<User> implements UserService {

	private static final long serialVersionUID = 1L;

	@Autowired
	private RoleDao roleDao;
	@Autowired
	private GroupDao groupDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserManager userManager;
	@Autowired
	private SystemConfigService systemConfigService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private RedisDataService redisDataService;

	@Override
	public Dao<User> getDao() {
		return userDao;
	}

	@Override
	public List<Role> getUserRoles(String userId) {
		return roleDao.selectRoleInUser(userId, null, null);
	}

	@Override
	public List<Group> getUserGroups(String userId) {
		Group param = new Group();
		param.setEnable(true);
		return groupDao.selectGroupInUser(userId, param);
	}

	@Override
	public boolean isAdmin(String userId) {
		User user = this.getUserInfo(userId);
		if (user != null) {
			String adminRoleIds = systemConfigService.getStringValue(SystemConfigConst.ADMIN_ROLE_IDS);
			String[] roleIds = adminRoleIds.split(",");
			for (String roleId : roleIds) {
				if (!StringUtil.isNullOrEmpty(roleId)) {
					List<User> adminUserInfos = roleService.getRoleUsers(roleId);
					if (adminUserInfos != null) {
						for (User adminUserInfo : adminUserInfos) {
							if (adminUserInfo.getId().equals(userId)) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean isSuperAdmin(String userId) {
		return false;
	}

	@Override
	public UserInfo getUserInfoByAccount(String account) {
		Assert.notNull(account, "帐户登录名不允许为空。");

		String key = UserInfo.REDIS__PREFIX_ACCOUNT_USER + account;
		UserInfo userInfo = redisDataService.getKeyObject(key, UserInfo.class);
		if (userInfo != null) {
			return userInfo;
		} else {
			User user = userDao.selectUserByAccount(account);
			UserInfo ui = new UserInfo();
			ui.setUserId(user.getId());
			ui.setUserName(user.getNickName());
			redisDataService.setKey(key, JsonUtil.toJson(ui));
			return ui;
		}
	}

	@Override
	public List<User> getUserByKey(String key) {
		Assert.notNull(key, "传入关键字不允许为空");

		List<User> users = userDao.selectUserByKey(key);
		return users;
	}

	private User getUserInfo(String userId) {
		if (StringUtil.isNullOrEmpty(userId)) {
			return null;
		}

		User userInfo = userManager.get(userId);
		if (userInfo != null) {
			return userInfo;
		} else {
			return null;
		}
	}
}
