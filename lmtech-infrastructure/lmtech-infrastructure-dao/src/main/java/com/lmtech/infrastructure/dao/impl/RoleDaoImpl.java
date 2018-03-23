package com.lmtech.infrastructure.dao.impl;

import com.lmtech.dao.MyBatisDaoBase;
import com.lmtech.infrastructure.dao.RoleDao;
import com.lmtech.infrastructure.mapper.RoleMapper;
import com.lmtech.infrastructure.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huang.jb on 2017-2-28.
 */
@Service
public class RoleDaoImpl extends MyBatisDaoBase<RoleMapper, Role> implements RoleDao {

    @Override
    public List<Role> selectRoleInUser(String userId, Role param, List<String> excludeRoleIds) {
        return baseMapper.selectRoleInUser(userId, param, excludeRoleIds);
    }

    @Override
    public List<Role> selectRoleNotInUser(String userId, Role param, List<String> excludeRoleIds) {
        return baseMapper.selectRoleNotInUser(userId, param, excludeRoleIds);
    }

    @Override
    public List<Role> selectAllValidRole() {
        return baseMapper.selectAllValidRole();
    }

    @Override
    public int selectRoleNameCount(String roleName) {
        return baseMapper.selectRoleNameCount(roleName);
    }

    @Override
    public void deleteRoleRelation(String roleId) {
        baseMapper.deleteRoleRelation(roleId);
    }
}
