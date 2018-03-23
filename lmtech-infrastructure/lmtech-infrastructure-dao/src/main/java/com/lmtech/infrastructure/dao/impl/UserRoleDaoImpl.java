package com.lmtech.infrastructure.dao.impl;

import com.lmtech.dao.MyBatisDaoBase;
import com.lmtech.infrastructure.dao.UserRoleDao;
import com.lmtech.infrastructure.mapper.UserRoleMapper;
import com.lmtech.infrastructure.model.UserRole;
import org.springframework.stereotype.Service;

/**
 * Created by huang.jb on 2017-4-14.
 */
@Service
public class UserRoleDaoImpl extends MyBatisDaoBase<UserRoleMapper, UserRole> implements UserRoleDao {
}
