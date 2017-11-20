package com.lmtech.infrastructure.dao.impl;

import com.lmtech.dao.MyBatisDaoBase;
import com.lmtech.infrastructure.dao.MenuDao;
import com.lmtech.infrastructure.mapper.MenuMapper;
import com.lmtech.infrastructure.model.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huang.jb on 2017-2-28.
 */
@Service
public class MenuDaoImpl extends MyBatisDaoBase<MenuMapper, Menu> implements MenuDao {

    @Override
    public List<Menu> selectMenuInRole(String roleId, Menu param) {
        return baseMapper.selectMenuInRole(roleId, param);
    }

    @Override
    public List<Menu> selectMenuNotInRole(String roleId, Menu param) {
        return baseMapper.selectMenuNotInRole(roleId, param);
    }

    @Override
    public List<Menu> selectEnableMenus() {
        return baseMapper.selectEnableMenus();
    }

    @Override
    public List<Menu> selectSubEnableMenus(String parentId) {
        return baseMapper.selectSubEnableMenus(parentId);
    }

    @Override
    public List<Menu> selectAllMenu() {
        return baseMapper.selectAllMenu();
    }

    @Override
    public List<Menu> selectAllValidMenu() {
        return baseMapper.selectAllValidMenu();
    }

    @Override
    public List<Menu> selectMenuExcludeIds(List<String> menuIds) {
        return baseMapper.selectMenuExcludeIds(menuIds);
    }

    @Override
    public void deleteMenuRelation(String menuId) {
        baseMapper.deleteMenuRelation(menuId);
    }
}
