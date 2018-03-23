package com.lmtech.infrastructure.dao.impl;

import com.lmtech.dao.MyBatisDaoBase;
import com.lmtech.infrastructure.dao.AdminPermissionDao;
import com.lmtech.infrastructure.mapper.AdminPermissionMapper;
import com.lmtech.infrastructure.model.AdminPermission;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huang.jb on 2017-2-28.
 */
@Service
public class AdminPermissionDaoImpl extends MyBatisDaoBase<AdminPermissionMapper, AdminPermission> implements AdminPermissionDao {

    @Override
    public List<String> selectResourceIdByType(String resourceType) {
        return baseMapper.selectResourceIdByType(resourceType);
    }
}
