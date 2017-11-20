package com.lmtech.infrastructure.service.impl;

import com.lmtech.dao.Dao;
import com.lmtech.infrastructure.dao.GroupDao;
import com.lmtech.infrastructure.dao.UserDao;
import com.lmtech.infrastructure.model.Group;
import com.lmtech.infrastructure.model.User;
import com.lmtech.infrastructure.service.GroupService;
import com.lmtech.service.support.AbstractDbServiceBaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl extends AbstractDbServiceBaseImpl<Group> implements GroupService {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UserDao userDao;
	@Autowired
	private GroupDao groupDao;

	@Override
	public Dao<Group> getDao() {
		return groupDao;
	}

	@Override
	public List<User> queryGroupUsers(String groupId) {
		//TODO 根据当前登录用户权限判断是否有权限查询
		User param = new User();
		param.setEnable(true);
		return userDao.selectUserInGroup(groupId, param, null);
	}

	@Override
	public List<Group> queryRootGroup() {
		return groupDao.selectRootGroup();
	}

	@Override
	public List<Group> queryChildrenGroup(String parentId) {
		return groupDao.selectChildrenGroup(parentId);
	}
}
