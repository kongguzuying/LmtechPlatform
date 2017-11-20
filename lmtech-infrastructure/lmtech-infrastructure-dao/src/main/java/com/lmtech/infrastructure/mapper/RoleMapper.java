package com.lmtech.infrastructure.mapper;

import com.lmtech.dao.LmtechBaseMapper;
import com.lmtech.infrastructure.model.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色Mapper
 * Created by huang.jb on 2017-2-9.
 */
public interface RoleMapper extends LmtechBaseMapper<Role> {
    /**
     * 获取用户下的角色
     * @param userId
     * @param param
     * @return
     */
    List<Role> selectRoleInUser(@Param("userId") String userId, @Param("param") Role param, @Param("excludeRoleIds") List<String> excludeRoleIds);

    /**
     * 获取不在用户下的角色
     * @param userId
     * @param param
     * @return
     */
    List<Role> selectRoleNotInUser(@Param("userId") String userId, @Param("param") Role param, @Param("excludeRoleIds") List<String> excludeRoleIds);

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
    int selectRoleNameCount(@Param("roleName") String roleName);

    /**
     * 删除角色关联关系
     * @param roleId
     */
    void deleteRoleRelation(String roleId);
}
