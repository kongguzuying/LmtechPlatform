package com.lmtech.infrastructure.service.impl;

import com.lmtech.dao.Dao;
import com.lmtech.infrastructure.dao.AdminPermissionDao;
import com.lmtech.infrastructure.model.AdminPermission;
import com.lmtech.infrastructure.service.AdminPermissionService;
import com.lmtech.service.support.AbstractDbServiceBaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminPermissionServiceImpl extends AbstractDbServiceBaseImpl<AdminPermission> implements AdminPermissionService {

	private static final long serialVersionUID = 1L;

	@Autowired
	private AdminPermissionDao adminPermissionDao;

	@Override
	public Dao<AdminPermission> getDao() {
		return adminPermissionDao;
	}

	@Override
	public List<String> getAdminSystemConfigs() {
		return adminPermissionDao.selectResourceIdByType(AdminPermission.RESOURCE_TYPE_SYSTEMCONFIG);
	}

	@Override
	public List<String> getAdminCodeTypes() {
		return adminPermissionDao.selectResourceIdByType(AdminPermission.RESOURCE_TYPE_CODETYPE);
	}

	@Override
	public List<String> getAdminMenus() {
		return adminPermissionDao.selectResourceIdByType(AdminPermission.RESOURCE_TYPE_MENU);
	}

	@Override
	public List<String> getAdminRoles() {
		return adminPermissionDao.selectResourceIdByType(AdminPermission.RESOURCE_TYPE_ROLE);
	}

	@Override
	public List<String> getAdminUsers() {
		return adminPermissionDao.selectResourceIdByType(AdminPermission.RESOURCE_TYPE_USER);
	}

}
