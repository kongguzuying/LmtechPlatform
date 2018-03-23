package com.lmtech.infrastructure.dao.impl;

import com.lmtech.dao.MyBatisDaoBase;
import com.lmtech.infrastructure.dao.RoleMenuPermissionDao;
import com.lmtech.infrastructure.mapper.RoleMenuPermissionMapper;
import com.lmtech.infrastructure.model.RoleMenuPermission;
import org.springframework.stereotype.Service;

/**
 *
 * Created by huang.jb on 2017-4-14.
 */
@Service
public class RoleMenuPermissionDaoImpl extends MyBatisDaoBase<RoleMenuPermissionMapper, RoleMenuPermission> implements RoleMenuPermissionDao {
}
