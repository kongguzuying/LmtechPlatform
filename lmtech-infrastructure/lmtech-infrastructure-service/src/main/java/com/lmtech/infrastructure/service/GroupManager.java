package com.lmtech.infrastructure.service;

import java.util.List;
import java.util.Map;

import com.lmtech.common.PageData;
import com.lmtech.infrastructure.model.Group;
import com.lmtech.infrastructure.model.User;
import com.lmtech.service.DbManagerBase;

/**
 * 群组管理
 * @author huang.jb
 *
 */
public interface GroupManager extends DbManagerBase<Group> {
	/**
	 * 获取群组用户
	 * @param groupId
	 */
	List<User> getGroupUsers(String groupId);

	/**
	 * 获取群组用户
	 * @param groupId
	 * @param args
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageData getGroupUsers(String groupId, Map<String, Object> args, int pageIndex, int pageSize);
	/**
	 * 获取未分配群组用户
	 * @param groupId
	 * @param args
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageData getGroupUnauthUsers(String groupId, Map<String, Object> args, int pageIndex, int pageSize);
	/**
	 * 设置群组用户
	 * @param groupId
	 * @param userIds
	 */
	void setGroupUsers(String groupId, List<String> userIds);
	/**
	 * 添加群组用户
	 * @param groupId
	 * @param userIds
	 */
	void addGroupUsers(String groupId, List<String> userIds);
	/**
	 * 删除群组用户
	 * @param groupId
	 * @param userIds
	 */
	void removeGroupUsers(String groupId, List<String> userIds);
}
