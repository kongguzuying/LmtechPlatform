package com.lmtech.infrastructure.service.impl;

import com.lmtech.dao.Dao;
import com.lmtech.infrastructure.dao.MenuDao;
import com.lmtech.infrastructure.model.Menu;
import com.lmtech.infrastructure.service.AdminPermissionService;
import com.lmtech.infrastructure.service.MenuManager;
import com.lmtech.service.support.AbstractDbNotAdminManagerBaseImpl;
import com.lmtech.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service
public class MenuManagerImpl extends AbstractDbNotAdminManagerBaseImpl<Menu> implements MenuManager {

	private static final long serialVersionUID = 1L;

	@Autowired
	private AdminPermissionService adminPermissionService;
	@Autowired
	private MenuDao menuDao;

	@Override
	public Dao<Menu> getDao() {
		return menuDao;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> getAll() {
		return menuDao.selectAllMenu();
	}

	@Override
	public List<?> getAllNotAdmin(Map<String, Object> params) {
		List<String> resourceIds = this.getAdminPermission();
		if (!CollectionUtil.isNullOrEmpty(resourceIds)) {
			return menuDao.selectMenuExcludeIds(resourceIds);
		} else {
			return menuDao.selectAllMenu();
		}
	}
		
	@Override
	public void remove(Serializable id) {
		Assert.notNull(id, "角色菜单不允许为空。");

		//删除角色菜单关联
		menuDao.deleteMenuRelation(id.toString());
		
		super.remove(id);
	}

	@Override
	public List<String> getAdminPermission() {
		return adminPermissionService.getAdminMenus();
	}

	//getter and setter
	public AdminPermissionService getAdminPermissionService() {
		return adminPermissionService;
	}

	public void setAdminPermissionService(AdminPermissionService adminPermissionService) {
		this.adminPermissionService = adminPermissionService;
	}
}
