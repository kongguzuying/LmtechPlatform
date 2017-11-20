package com.lmtech.infrastructure.dao;

import com.lmtech.dao.Dao;
import com.lmtech.infrastructure.model.Role;

import java.util.List;

/**
 * 角色Dao
 * Created by huang.jb on 2017-2-28.
 */
public interface RoleDao extends Dao<Role> {
    /**
     * 获取用户下的角色
     * @param userId
     * @param param
     * @return
     */
    List<Role> selectRoleInUser(String userId, Role param, List<String> excludeRoleIds);

    /**
     * 获取不在用户下的角色
     * @param userId
     * @param param
     * @return
     */
    List<Role> selectRoleNotInUser(String userId, Role param, List<String> excludeRoleIds);

    /**
     * 获取所有已校验过的角色
     * @return
     */
    List<Role> selectAllValidRole();

    /**
     * 获取同名角色数
     * @param roleName
     * @return
     */
    int selectRoleNameCount(String roleName);

    /**
     * 删除角色关联关系
     * @param roleId
     */
    void deleteRoleRelation(String roleId);
}
