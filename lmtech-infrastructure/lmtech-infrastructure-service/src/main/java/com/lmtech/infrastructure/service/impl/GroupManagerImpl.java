package com.lmtech.infrastructure.service.impl;

import com.lmtech.common.PageData;
import com.lmtech.dao.Dao;
import com.lmtech.infrastructure.dao.GroupDao;
import com.lmtech.infrastructure.dao.UserDao;
import com.lmtech.infrastructure.model.Group;
import com.lmtech.infrastructure.model.GroupUser;
import com.lmtech.infrastructure.model.User;
import com.lmtech.infrastructure.service.GroupManager;
import com.lmtech.service.support.AbstractDbMTMRelationManagerBaseImpl;
import com.lmtech.util.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class GroupManagerImpl extends AbstractDbMTMRelationManagerBaseImpl<Group> implements GroupManager {

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
	public List<User> getGroupUsers(String groupId) {
		List<User> users = userDao.selectUserInGroup(groupId, null, null);
		return users;
	}

	@Override
	public PageData getGroupUsers(String groupId, Map<String, Object> args, int pageIndex, int pageSize) {
		PageHelper.startPage(pageIndex, pageSize);
		userDao.selectUserInGroup(groupId, null, null);
		return PageHelper.endPage();
	}

	@Override
	public PageData getGroupUnauthUsers(String groupId, Map<String, Object> args, int pageIndex, int pageSize) {
		PageHelper.startPage(pageIndex, pageSize);
		userDao.selectUserNotInGroup(groupId, null, null);
		return PageHelper.endPage();
	}
	
	@Override
	@Transactional
	public void setGroupUsers(String groupId, List<String> userIds) {
		super.setOneToManyRelation(groupId, userIds, "groupId", "userId", GroupUser.class);
	}

	@Override
	public void addGroupUsers(String groupId, List<String> userIds) {
		super.addOneToManyRelation(groupId, userIds, "groupId", "userId", GroupUser.class);
	}

	@Override
	public void removeGroupUsers(String groupId, List<String> userIds) {
		super.removeOneToManyRelation(groupId, userIds, "groupId", "userId", GroupUser.class);
	}
}
