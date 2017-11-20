package com.lmtech.infrastructure.dao.impl;

import com.lmtech.dao.MyBatisDaoBase;
import com.lmtech.infrastructure.dao.UserDao;
import com.lmtech.infrastructure.mapper.UserMapper;
import com.lmtech.infrastructure.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huang.jb on 2017-2-28.
 */
@Service
public class UserDaoImpl extends MyBatisDaoBase<UserMapper, User> implements UserDao {

    @Override
    public List<User> selectUserInGroup(String groupId, User param, List<String> excludeUserIds) {
        return baseMapper.selectUserInGroup(groupId, param, excludeUserIds);
    }

    @Override
    public List<User> selectUserNotInGroup(String groupId, User param, List<String> excludeUserIds) {
        return baseMapper.selectUserNotInGroup(groupId, param, excludeUserIds);
    }

    @Override
    public List<User> selectUserInRole(String roleId, User param, List<String> excludeUserIds) {
        return baseMapper.selectUserInRole(roleId, param, excludeUserIds);
    }

    @Override
    public List<User> selectUserNotInRole(String roleId, User param, List<String> excludeUserIds) {
        return baseMapper.selectUserNotInRole(roleId, param, excludeUserIds);
    }

    @Override
    public void deleteUserRelation(String userId) {
        baseMapper.deleteUserRelation(userId);
    }

    @Override
    public User selectUserByAccount(String account) {
        return baseMapper.selectUserByAccount(account);
    }

    @Override
    public List<User> selectUserByKey(String key) {
        return baseMapper.selectUserByKey(key);
    }
}
