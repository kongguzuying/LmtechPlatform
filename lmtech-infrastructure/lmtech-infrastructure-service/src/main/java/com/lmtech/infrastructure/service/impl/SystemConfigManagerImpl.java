package com.lmtech.infrastructure.service.impl;

import java.util.List;

import com.lmtech.dao.Dao;
import com.lmtech.infrastructure.dao.SystemConfigDao;
import com.lmtech.infrastructure.model.SystemConfig;
import com.lmtech.infrastructure.service.AdminPermissionService;
import com.lmtech.infrastructure.service.SystemConfigManager;
import com.lmtech.service.support.AbstractDbNotAdminManagerBaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemConfigManagerImpl extends AbstractDbNotAdminManagerBaseImpl<SystemConfig> implements SystemConfigManager {

	private static final long serialVersionUID = 1L;

	@Autowired
	private AdminPermissionService adminPermissionService;
	@Autowired
	private SystemConfigDao systemConfigDao;
	
	@Override
	public List<String> getAdminPermission() {
		return adminPermissionService.getAdminSystemConfigs();
	}

	@Override
	public Dao<SystemConfig> getDao() {
		return systemConfigDao;
	}
}
