package com.lmtech.infrastructure.mapper;

import com.lmtech.dao.LmtechBaseMapper;
import com.lmtech.infrastructure.model.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单Mapper
 * Created by huang.jb on 2017-2-9.
 */
public interface MenuMapper extends LmtechBaseMapper<Menu> {
    /***
     * 搜索角色下的菜单
     * @param roleId
     * @param param
     * @return
     */
    List<Menu> selectMenuInRole(@Param("roleId") String roleId, @Param("param") Menu param);

    /**
     * 搜索不在角色下的菜单
     * @param roleId
     * @param param
     * @return
     */
    List<Menu> selectMenuNotInRole(@Param("roleId") String roleId, @Param("param") Menu param);

    /**
     * 搜索所有可用的菜单
     * @return
     */
    List<Menu> selectEnableMenus();

    /**
     * 搜索所有可用的子菜单
     * @param parentId
     * @return
     */
    List<Menu> selectSubEnableMenus(@Param("parentId") String parentId);

    /**
     * 搜索所有菜单
     * @return
     */
    List<Menu> selectAllMenu();

    /**
     * 搜索所有已校验的菜单
     * @return
     */
    List<Menu> selectAllValidMenu();

    /**
     * 搜索排队部分id的菜单
     * @param menuIds
     * @return
     */
    List<Menu> selectMenuExcludeIds(@Param("menuIds") List<String> menuIds);

    /**
     * 删除菜单相关联数据
     * @param menuId
     */
    void deleteMenuRelation(@Param("menuId") String menuId);
}
