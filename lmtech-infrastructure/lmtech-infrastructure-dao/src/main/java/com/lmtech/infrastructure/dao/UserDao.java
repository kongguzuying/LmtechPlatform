package com.lmtech.infrastructure.dao;

import com.lmtech.dao.Dao;
import com.lmtech.infrastructure.model.User;

import java.util.List;

/**
 * 用户Dao
 * Created by huang.jb on 2017-2-28.
 */
public interface UserDao extends Dao<User> {
    /**
     * 获取分组下的用户
     * @param groupId
     * @param param
     * @return
     */
    List<User> selectUserInGroup(String groupId, User param, List<String> excludeUserIds);

    /**
     * 获取不在分组下的用户
     * @param groupId
     * @param param
     * @return
     */
    List<User> selectUserNotInGroup(String groupId, User param, List<String> excludeUserIds);

    /**
     * 获取角色下的用户
     * @param roleId
     * @param param
     * @return
     */
    List<User> selectUserInRole(String roleId, User param, List<String> excludeUserIds);

    /**
     * 获取不在角色下的用户
     * @param roleId
     * @param param
     * @return
     */
    List<User> selectUserNotInRole(String roleId, User param, List<String> excludeUserIds);

    /**
     * 删除用户关联数据
     * @param userId
     */
    void deleteUserRelation(String userId);

    /**
     * 通过帐户获取用户信息
     * @param account
     * @return
     */
    User selectUserByAccount(String account);

    /**
     * 通过关键字获取用户数据
     * @param key
     * @return
     */
    List<User> selectUserByKey(String key);
}
