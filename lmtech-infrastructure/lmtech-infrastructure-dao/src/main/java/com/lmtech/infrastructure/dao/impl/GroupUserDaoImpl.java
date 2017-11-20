package com.lmtech.infrastructure.dao.impl;

import com.lmtech.dao.MyBatisDaoBase;
import com.lmtech.infrastructure.dao.GroupUserDao;
import com.lmtech.infrastructure.mapper.GroupUserMapper;
import com.lmtech.infrastructure.model.GroupUser;
import org.springframework.stereotype.Service;

/**
 *
 * Created by huang.jb on 2017-4-14.
 */
@Service
public class GroupUserDaoImpl extends MyBatisDaoBase<GroupUserMapper, GroupUser> implements GroupUserDao {
}
