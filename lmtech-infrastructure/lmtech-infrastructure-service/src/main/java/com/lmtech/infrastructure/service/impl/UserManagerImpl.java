package com.lmtech.infrastructure.service.impl;

import com.lmtech.common.PageData;
import com.lmtech.dao.Dao;
import com.lmtech.infrastructure.dao.RoleDao;
import com.lmtech.infrastructure.dao.UserDao;
import com.lmtech.infrastructure.model.User;
import com.lmtech.infrastructure.model.UserRole;
import com.lmtech.infrastructure.service.AdminPermissionService;
import com.lmtech.infrastructure.service.UserManager;
import com.lmtech.service.support.AbstractDbMTMRelationNotAdminManagerBaseImpl;
import com.lmtech.util.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service
public class UserManagerImpl extends AbstractDbMTMRelationNotAdminManagerBaseImpl<User> implements UserManager {

	private static final long serialVersionUID = 1L;

	@Autowired
	private RoleDao roleDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private AdminPermissionService adminPermissionService;

	@Override
	public Dao<User> getDao() {
		return userDao;
	}
	
	@Override
	public void remove(Serializable id) {
		if (id == null) {
			throw new RuntimeException("id could not be null when remove!");
		}

		//删除用户关联
		userDao.deleteUserRelation(String.valueOf(id));
		
		super.remove(id);
	}
	
	@Override
	public PageData getUserRoles(String userId, Map<Object, Object> params, int pageIndex, int pageSize) {
		PageHelper.startPage(pageIndex, pageSize);
		roleDao.selectRoleInUser(userId, null, null);
		return PageHelper.endPage();
	}

	@Override
	public PageData getUserUnauthRoles(String userId, Map<Object, Object> params, int pageIndex, int pageSize) {
		PageHelper.startPage(pageIndex, pageSize);
		roleDao.selectRoleNotInUser(userId, null, null);
		return PageHelper.endPage();
	}

	@Override
	public PageData getUserRolesNotAdmin(String userId, Map<Object, Object> params, int pageIndex, int pageSize) {
		List<String> resourceIds = this.getAdminPermission();
		PageHelper.startPage(pageIndex, pageSize);
		roleDao.selectRoleInUser(userId, null, resourceIds);
		return PageHelper.endPage();
	}

	@Override
	public PageData getUserUnauthRolesNotAdmin(String userId, Map<Object, Object> params, int pageIndex, int pageSize) {
		List<String> resourceIds = this.getAdminPermission();
		PageHelper.startPage(pageIndex, pageSize);
		roleDao.selectRoleNotInUser(userId, null, resourceIds);
		return PageHelper.endPage();
	}
	
	@Override
	public void setUserRoles(String userId, List<String> roleIds) {
		super.setOneToManyRelation(userId, roleIds, "userId", "roleId", UserRole.class);
	}

	@Override
	public void addUserRoles(String userId, List<String> roleIds) {
		super.addOneToManyRelation(userId, roleIds, "userId", "roleId", UserRole.class);
	}

	@Override
	public void removeUserRoles(String userId, List<String> roleIds) {
		super.removeOneToManyRelation(userId, roleIds, "userId", "roleId", UserRole.class);
	}

	@Override
	public List<String> getAdminPermission() {
		return adminPermissionService.getAdminUsers();
	}

}
