package com.lmtech.infrastructure.service.impl;

import com.lmtech.common.PageData;
import com.lmtech.dao.Dao;
import com.lmtech.infrastructure.dao.*;
import com.lmtech.infrastructure.model.*;
import com.lmtech.infrastructure.service.AdminPermissionService;
import com.lmtech.infrastructure.service.RoleManager;
import com.lmtech.service.support.AbstractDbMTMRelationNotAdminManagerBaseImpl;
import com.lmtech.util.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service
public class RoleManagerImpl extends AbstractDbMTMRelationNotAdminManagerBaseImpl<Role> implements RoleManager {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UserDao userDao;
	@Autowired
	private ActionDao actionDao;
	@Autowired
	private MenuDao menuDao;
	@Autowired
	private ResourceDao resourceDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private AdminPermissionService adminPermissionService;

	@Override
	public Dao<Role> getDao() {
		return roleDao;
	}

	@Override
	public void remove(Serializable id) {
		if (id == null) {
			throw new RuntimeException("id should not be null when remove");
		}

		//删除角色关联数据
		roleDao.deleteRoleRelation(String.valueOf(id));

		super.remove(id);
	}
	
	@Override
	public PageData getRoleUsers(String roleId, Map<String, Object> args, int pageIndex, int pageSize) {
		PageHelper.startPage(pageIndex, pageSize);
		userDao.selectUserInRole(roleId, null, null);
		return PageHelper.endPage();
	}

	@Override
	public PageData getRoleActions(String roleId, Map<String, Object> args, int pageIndex, int pageSize) {
		PageHelper.startPage(pageIndex, pageSize);
		actionDao.selectActionInRole(roleId, null);
		return PageHelper.endPage();
	}
	
	@Override
	public PageData getRoleUnauthUsers(String roleId, Map<String, Object> args, int pageIndex, int pageSize) {
		PageHelper.startPage(pageIndex, pageSize);
		userDao.selectUserNotInRole(roleId, null, null);
		return PageHelper.endPage();
	}

	@Override
	public PageData getRoleUsersNotAdmin(String roleId, Map<String, Object> params, int pageIndex, int pageSize) {
		List<String> resourceIds = this.getAdminPermission();
		PageHelper.startPage(pageIndex, pageSize);
		userDao.selectUserInRole(roleId, null, resourceIds);
		return PageHelper.endPage();
	}

	@Override
	public PageData getRoleUnauthUsersNotAdmin(String roleId, Map<String, Object> params, int pageIndex, int pageSize) {
		List<String> resourceIds = this.getAdminPermission();
		PageHelper.startPage(pageIndex, pageSize);
		userDao.selectUserNotInRole(roleId, null, resourceIds);
		return PageHelper.endPage();
	}

	@Override
	public List<Menu> getRoleUnauthMenus(String roleId) {
		return menuDao.selectMenuNotInRole(roleId, null);
	}

	@Override
	public List<Menu> getRoleMenus(String roleId) {
		return menuDao.selectMenuInRole(roleId, null);
	}

	@Override
	public PageData getRoleUnauthActions(String roleId, Map<String, Object> args, int pageIndex, int pageSize) {
		PageHelper.startPage(pageIndex, pageSize);
		actionDao.selectActionNotInRole(roleId, null);
		return PageHelper.endPage();
	}

	@Override
	public void setRoleUsers(String roleId, List<String> userIds) {
		super.setOneToManyRelation(roleId, userIds, "roleId", "userId", UserRole.class);
	}

	@Override
	public void addRoleUsers(String roleId, List<String> userIds) {
		super.addOneToManyRelation(roleId, userIds, "roleId", "userId", UserRole.class);
	}

	@Override
	public void removeRoleUsers(String roleId, List<String> userIds) {
		super.removeOneToManyRelation(roleId, userIds, "roleId", "userId", UserRole.class);
	}

	@Override
	public void setRoleMenus(String roleId, List<String> menuIds) {
		super.setOneToManyRelation(roleId, menuIds, "roleId", "menuId", RoleMenuPermission.class);
	}

	@Override
	public void addRoleMenus(String roleId, List<String> menuIds) {
		super.addOneToManyRelation(roleId, menuIds, "roleId", "menuId", RoleMenuPermission.class);
	}

	@Override
	public void removeRoleMenus(String roleId, List<String> menuIds) {
		super.removeOneToManyRelation(roleId, menuIds, "roleId", "menuId", RoleMenuPermission.class);
	}

	@Override
	public PageData getRoleResources(String roleId, Map<String, Object> args, int pageIndex, int pageSize) {
		PageHelper.startPage(pageIndex, pageSize);
		resourceDao.selectResourceInRole(roleId, null);
		return PageHelper.endPage();
	}

	@Override
	public PageData getRoleUnauthResources(String roleId, Map<String, Object> args, int pageIndex, int pageSize) {
		PageHelper.startPage(pageIndex, pageSize);
		resourceDao.selectResourceNotInRole(roleId, null);
		return PageHelper.endPage();
	}

	@Override
	public void setRoleResources(String roleId, List<String> resourceIds) {
		super.setOneToManyRelation(roleId, resourceIds, "roleId", "resourceId", RoleResourcePermission.class);
	}

	@Override
	public void addRoleResources(String roleId, List<String> resourceIds) {
		super.addOneToManyRelation(roleId, resourceIds, "roleId", "resourceId", RoleResourcePermission.class);
	}

	@Override
	public void removeRoleResources(String roleId, List<String> resourceIds) {
		super.removeOneToManyRelation(roleId, resourceIds, "roleId", "resourceId", RoleResourcePermission.class);
	}

	@Override
	public void setRoleActions(String roleId, List<String> actionIds) {
		super.setOneToManyRelation(roleId, actionIds, "roleId", "actionId", RoleActionPermission.class);
	}

	@Override
	public void addRoleActions(String roleId, List<String> actionIds) {
		super.addOneToManyRelation(roleId, actionIds, "roleId", "actionId", RoleActionPermission.class);
	}

	@Override
	public void removeRoleActions(String roleId, List<String> actionIds) {
		super.removeOneToManyRelation(roleId, actionIds, "roleId", "actionId", RoleActionPermission.class);
	}

	@Override
	public List<String> getAdminPermission() {
		return adminPermissionService.getAdminRoles();
	}

}
