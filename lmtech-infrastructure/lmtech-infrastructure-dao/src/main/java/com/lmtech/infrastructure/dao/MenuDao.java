package com.lmtech.infrastructure.dao;

import com.lmtech.dao.Dao;
import com.lmtech.infrastructure.model.Menu;

import java.util.List;

/**
 * 菜单Dao
 * Created by huang.jb on 2017-2-28.
 */
public interface MenuDao extends Dao<Menu> {
    /***
     * 获取角色下的菜单
     * @param roleId
     * @param param
     * @return
     */
    List<Menu> selectMenuInRole(String roleId, Menu param);

    /**
     * 获取不在角色下的菜单
     * @param roleId
     * @param param
     * @return
     */
    List<Menu> selectMenuNotInRole(String roleId, Menu param);

    /**
     * 获取所有可用的菜单
     * @return
     */
    List<Menu> selectEnableMenus();

    /**
     * 获取所有可用的子菜单
     * @param parentId
     * @return
     */
    List<Menu> selectSubEnableMenus(String parentId);

    /**
     * 获取所有菜单
     * @return
     */
    List<Menu> selectAllMenu();

    /**
     * 获取所有已校验的菜单
     * @return
     */
    List<Menu> selectAllValidMenu();

    /**
     * 搜索排队部分id的菜单
     * @param menuIds
     * @return
     */
    List<Menu> selectMenuExcludeIds(List<String> menuIds);

    /**
     * 删除菜单相关联数据
     * @param menuId
     */
    void deleteMenuRelation(String menuId);
}
