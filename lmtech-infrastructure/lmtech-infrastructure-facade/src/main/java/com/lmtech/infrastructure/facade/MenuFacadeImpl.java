package com.lmtech.infrastructure.facade;

import com.lmtech.facade.request.NormalRequest;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import com.lmtech.infrastructure.facade.request.MenuRequest;
import com.lmtech.infrastructure.facade.response.MenuListResponse;
import com.lmtech.infrastructure.facade.response.MenuResponse;
import com.lmtech.infrastructure.facade.stub.MenuFacade;
import com.lmtech.infrastructure.model.Menu;
import com.lmtech.infrastructure.service.MenuManager;
import com.lmtech.infrastructure.service.MenuService;
import com.lmtech.util.ComplexUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by huang.jb on 2017-3-14.
 */
@RestController
@RequestMapping(value = "/menu")
@Api(description = "菜单API入口")
public class MenuFacadeImpl implements MenuFacade {

    @Autowired
    private MenuService menuService;
    @Autowired
    private MenuManager menuManager;

    @Override
    @RequestMapping(value = "/queryUserMenus", method = RequestMethod.POST)
    @ApiOperation(value = "查询当前用户相关菜单")
    public MenuListResponse queryUserMenus(@RequestBody StringRequest request) {
        String userId = request.getReqData();
        List<Menu> menus = menuService.getUserTopMenu(userId);

        MenuListResponse response = new MenuListResponse();
        response.setSuccess(true);
        response.setData(menus);
        response.setMessage("查询用户菜单成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/queryAllValidMenus", method = RequestMethod.POST)
    @ApiOperation(value = "查询所有校验过的菜单")
    public MenuListResponse queryAllValidMenus(@RequestBody NormalRequest request) {
        List<Menu> menus = menuService.getAllValidMenu();

        MenuListResponse response = new MenuListResponse();
        response.setSuccess(true);
        response.setData(menus);
        response.setMessage("查询已校验菜单成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/getMenu", method = RequestMethod.POST)
    @ApiOperation(value = "获取菜单数据")
    public MenuResponse getMenu(@RequestBody StringRequest request) {
        String id = request.getReqData();

        Menu menu = menuManager.get(id);

        MenuResponse response = new MenuResponse();
        response.setSuccess(true);
        response.setData(menu);
        response.setMessage("获取菜单成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/getAllMenu", method = RequestMethod.POST)
    @ApiOperation(value = "获取所有菜单数据")
    public MenuListResponse getAllMenu(@RequestBody NormalRequest request) {
        List<Menu> menus = menuManager.getAll();

        MenuListResponse response = new MenuListResponse();
        response.setSuccess(true);
        response.setData(menus);
        response.setMessage("获取所有菜单成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/getAllSortMenu", method = RequestMethod.POST)
    @ApiOperation(value = "获取所有排序菜单数据")
    public MenuListResponse getAllSortMenu(@RequestBody NormalRequest request) {
        List<Menu> menus = menuManager.getAll();
        ComplexUtil<Menu> util = new ComplexUtil<>();
        List<Menu> ts = util.convertToComplex(menus);
        for (Menu item : ts) {
            //将顶层菜单父id置0
            item.setParentId("0");
        }
        menus = util.convertToList(ts);

        MenuListResponse response = new MenuListResponse();
        response.setSuccess(true);
        response.setData(menus);
        response.setMessage("获取所有排序菜单成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/addMenu", method = RequestMethod.POST)
    @ApiOperation(value = "添加菜单")
    public StringResponse addMenu(@RequestBody MenuRequest request) {
        Menu menu = request.getReqData();

        String id = menuManager.add(menu);

        StringResponse response = new StringResponse();
        response.setSuccess(true);
        response.setData(id);
        response.setMessage("添加菜单成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/editMenu", method = RequestMethod.POST)
    @ApiOperation(value = "编辑菜单")
    public StringResponse editMenu(@RequestBody MenuRequest request) {
        Menu menu = request.getReqData();
        Assert.notNull(menu.getId(), "传入菜单编号不允许为空");

        menuManager.edit(menu);

        StringResponse response = new StringResponse();
        response.setSuccess(true);
        response.setMessage("编辑菜单成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/removeMenu", method = RequestMethod.POST)
    @ApiOperation(value = "删除菜单")
    public NormalResponse removeMenu(@RequestBody StringRequest request) {
        String id = request.getReqData();

        menuManager.remove(id);

        NormalResponse response = new NormalResponse();
        response.setSuccess(true);
        response.setMessage("删除菜单成功");
        return response;
    }


}
