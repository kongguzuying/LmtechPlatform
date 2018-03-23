package com.lmtech.infrastructure.dao.impl;

import com.lmtech.dao.MyBatisDaoBase;
import com.lmtech.infrastructure.dao.GroupDao;
import com.lmtech.infrastructure.mapper.GroupMapper;
import com.lmtech.infrastructure.model.Group;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huang.jb on 2017-2-28.
 */
@Service
public class GroupDaoImpl extends MyBatisDaoBase<GroupMapper, Group> implements GroupDao {

    @Override
    public List<Group> selectGroupInUser(String userId, Group param) {
        return baseMapper.selectGroupInUser(userId, param);
    }

    @Override
    public List<Group> selectRootGroup() {
        return baseMapper.selectRootGroup();
    }

    @Override
    public List<Group> selectChildrenGroup(String parentId) {
        return baseMapper.selectChildrenGroup(parentId);
    }
}
