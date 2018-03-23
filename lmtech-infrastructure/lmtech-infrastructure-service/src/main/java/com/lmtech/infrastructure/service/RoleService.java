package com.lmtech.infrastructure.service;

import java.util.List;

import com.lmtech.infrastructure.model.*;
import com.lmtech.service.DbServiceBase;

/**
 * 角色服务
 *
 * @author haung.jb
 */
public interface RoleService extends DbServiceBase<Role> {
    /**
     * 获取角色相关用户
     *
     * @param roleId
     * @return
     */
    List<User> getRoleUsers(String roleId);

    /**
     * 获取角色相关菜单
     *
     * @param roleId
     * @return
     */
    List<Menu> getRoleMenus(String roleId);

    /**
     * 获取角色相关Action
     *
     * @param roleId
     * @return
     */
    List<Action> getRoleActions(String roleId);

    /**
     * 获取角色相关资源
     * @param roleId
     * @return
     */
    List<Resource> getRoleResources(String roleId);

    /**
     * 获取所有已校验的角色
     *
     * @return
     */
    List<Role> getAllValidRole();

    /**
     * 是否存在角色名称
     *
     * @param roleName
     * @return
     */
    boolean existRoleName(String roleName);
}
