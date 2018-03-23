package com.lmtech.infrastructure.service.impl;

import com.lmtech.dao.Dao;
import com.lmtech.infrastructure.dao.CodeItemDao;
import com.lmtech.infrastructure.dao.CodeTypeDao;
import com.lmtech.infrastructure.model.CodeItem;
import com.lmtech.infrastructure.model.CodeType;
import com.lmtech.infrastructure.service.AdminPermissionService;
import com.lmtech.infrastructure.service.CodeManager;
import com.lmtech.service.support.AbstractDbNotAdminManagerBaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class CodeManagerImpl extends AbstractDbNotAdminManagerBaseImpl<CodeType> implements CodeManager {
	private static final long serialVersionUID = 1L;

	@Autowired
	private CodeTypeDao codeTypeDao;
	@Autowired
	private CodeItemDao codeItemDao;
	@Autowired
	private AdminPermissionService adminPermissionService;

	@Override
	public Dao<CodeType> getDao() {
		return codeTypeDao;
	}

	@Override
	public void remove(Serializable id) {
		CodeType codeType = super.get(id);
		if (codeType != null) {
			this.removeCodeItemByTypeCode(codeType.getCode());
			super.remove(id);
		}
	}
	
	@Override
	public String addCodeItem(CodeItem t) {
		if (t != null) {
			String id = preHandleAddObjectEntity(t);
			codeItemDao.insert(t);
			return id;
		}
		return null;
	}

	@Override
	public void editCodeItem(CodeItem t) {
		if (t != null) {
			preHandleEditObjectEntity(t);
			codeItemDao.updateById(t);
		}
	}

	@Override
	public void removeCodeItem(Serializable id) {
		codeItemDao.deleteById(id);
	}

	@Override
	public CodeItem getCodeItem(Serializable id) {
		return codeItemDao.selectById(id);
	}

	@Override
	public List<CodeItem> getAllCodeItem() {
		return codeItemDao.selectAll();
	}

	@Override
	public List<CodeItem> getCodeItemOfType(String typeCode) {
		return codeItemDao.selectOfType(typeCode);
	}

	@Override
	public List<String> getAdminPermission() {
		return adminPermissionService.getAdminCodeTypes();
	}
	
	private void removeCodeItemByTypeCode(String typeCode) {
		codeItemDao.deleteByTypeCode(typeCode);
	}

	//getter and setter
	public AdminPermissionService getAdminPermissionService() {
		return adminPermissionService;
	}

	public void setAdminPermissionService(AdminPermissionService adminPermissionService) {
		this.adminPermissionService = adminPermissionService;
	}
}
