package com.lmtech.infrastructure.dao.impl;

import com.lmtech.dao.MyBatisDaoBase;
import com.lmtech.infrastructure.dao.ResourceDao;
import com.lmtech.infrastructure.mapper.ResourceMapper;
import com.lmtech.infrastructure.model.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huang.jb on 2017-4-20.
 */
@Service
public class ResourceDaoImpl extends MyBatisDaoBase<ResourceMapper, Resource> implements ResourceDao {
    @Override
    public List<Resource> selectResourceInRole(String roleId, Resource param) {
        return baseMapper.selectResourceInRole(roleId, param);
    }

    @Override
    public List<Resource> selectResourceNotInRole(String roleId, Resource param) {
        return baseMapper.selectResourceNotInRole(roleId, param);
    }

    @Override
    public void deleteResourceRelation(String resourceId) {
        baseMapper.deleteResourceRelation(resourceId);
    }
}
