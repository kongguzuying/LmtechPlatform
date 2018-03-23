package com.lmtech.infrastructure.service.impl;

import com.lmtech.dao.Dao;
import com.lmtech.infrastructure.dao.AdminPermissionDao;
import com.lmtech.infrastructure.model.AdminPermission;
import com.lmtech.infrastructure.service.AdminPermissionManager;
import com.lmtech.service.support.AbstractDbManagerBaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminPermissionManagerImpl extends AbstractDbManagerBaseImpl<AdminPermission> implements AdminPermissionManager {

	private static final long serialVersionUID = 1L;

	@Autowired
	private AdminPermissionDao adminPermissionDao;

	@Override
	public Dao<AdminPermission> getDao() {
		return adminPermissionDao;
	}
}
