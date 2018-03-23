package com.lmtech.infrastructure.service.impl;

import com.lmtech.dao.Dao;
import com.lmtech.infrastructure.dao.*;
import com.lmtech.infrastructure.model.*;
import com.lmtech.infrastructure.service.RoleService;
import com.lmtech.service.support.AbstractDbServiceBaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl extends AbstractDbServiceBaseImpl<Role> implements RoleService {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UserDao userDao;
	@Autowired
	private MenuDao menuDao;
	@Autowired
	private ActionDao actionDao;
	@Autowired
	private ResourceDao resourceDao;
	@Autowired
	private RoleDao roleDao;

	@Override
	public Dao<Role> getDao() {
		return roleDao;
	}

	@Override
	public List<User> getRoleUsers(String roleId) {
		return userDao.selectUserInRole(roleId, null, null);
	}

	@Override
	public List<Menu> getRoleMenus(String roleId) {
		return menuDao.selectMenuInRole(roleId, null);
	}

	@Override
	public List<Action> getRoleActions(String roleId) {
		return actionDao.selectActionInRole(roleId, null);
	}

	@Override
	public List<Resource> getRoleResources(String roleId) {
		return resourceDao.selectResourceInRole(roleId, null);
	}

	@Override
	public List<Role> getAllValidRole() {
		return roleDao.selectAllValidRole();
	}

	@Override
	public boolean existRoleName(String roleName) {
		int count = roleDao.selectRoleNameCount(roleName);
		return count > 0;
	}

}
