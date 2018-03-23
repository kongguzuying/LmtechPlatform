package com.lmtech.infrastructure.service;

import java.util.List;

import com.lmtech.infrastructure.model.Group;
import com.lmtech.infrastructure.model.User;
import com.lmtech.service.DbServiceBase;

/**
 * 组织服务
 * @author huang.jb
 *
 */
public interface GroupService extends DbServiceBase<Group> {
	/**
	 * 获取群组下的所有用户
	 * @param groupId
	 * @return
	 */
	List<User> queryGroupUsers(String groupId);

	/**
	 * 查询根群组
	 * @return
     */
	List<Group> queryRootGroup();

	/**
	 * 查询子群组
	 * @param parentId
     * @return
     */
	List<Group> queryChildrenGroup(String parentId);
}
