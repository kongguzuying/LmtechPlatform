package com.lmtech.admin.common.controller;

import com.lmtech.admin.common.adaptor.ControllerManager;
import com.lmtech.admin.common.adaptor.MenuAdaptor;
import com.lmtech.infrastructure.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 菜单控制器
 * Created by huang.jb on 2016-7-14.
 */
@Controller
@RequestMapping("/platform/menu")
public class MenuController extends ComplexControllerBase<Menu> {

    @Autowired
    private MenuAdaptor menuAdaptor;

    @Override
    public ControllerManager<Menu> getManager() {
        return menuAdaptor;
    }

    @Override
    public void fillEntity(Menu dbEntity, Menu pageEntity) {
        dbEntity.setParentId(pageEntity.getParentId());
        dbEntity.setAppCode(pageEntity.getAppCode());
        dbEntity.setHref(pageEntity.getHref());
        dbEntity.setMenuType(pageEntity.getMenuType());
        dbEntity.setName(pageEntity.getName());
        dbEntity.setSortNo(pageEntity.getSortNo());
        dbEntity.setIcon(pageEntity.getIcon());
        dbEntity.setTarget(pageEntity.getTarget());
        dbEntity.setVisible(pageEntity.getVisible());
    }

    @Override
    public String getRequestMapping() {
        return "platform/menu";
    }
}
