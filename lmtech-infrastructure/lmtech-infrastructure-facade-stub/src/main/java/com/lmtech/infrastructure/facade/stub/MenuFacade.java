package com.lmtech.infrastructure.facade.stub;

import com.lmtech.facade.request.NormalRequest;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import com.lmtech.infrastructure.facade.request.MenuRequest;
import com.lmtech.infrastructure.facade.response.MenuListResponse;
import com.lmtech.infrastructure.facade.response.MenuResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 菜单服务入口
 * Created by huang.jb on 2017-3-14.
 */
@FeignClient(name = "lmtech-infrastructure")
@RequestMapping(value = "/menu")
public interface MenuFacade {
    /**
     * 查询当前用户相关菜单
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryUserMenus", method = RequestMethod.POST)
    MenuListResponse queryUserMenus(StringRequest request);

    /**
     * 查询所有校验过的菜单
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryAllValidMenus", method = RequestMethod.POST)
    MenuListResponse queryAllValidMenus(NormalRequest request);

    /**
     * 获取菜单数据
     * @param request
     * @return
     */
    @RequestMapping(value = "/getMenu", method = RequestMethod.POST)
    MenuResponse getMenu(StringRequest request);

    /**
     * 获取所有菜单数据
     * @param request
     * @return
     */
    @RequestMapping(value = "/getAllMenu", method = RequestMethod.POST)
    MenuListResponse getAllMenu(NormalRequest request);

    /**
     * 获取所有排序菜单数据
     * @param request
     * @return
     */
    @RequestMapping(value = "/getAllSortMenu", method = RequestMethod.POST)
    MenuListResponse getAllSortMenu(NormalRequest request);

    /**
     * 添加菜单
     * @param request
     * @return
     */
    @RequestMapping(value = "/addMenu", method = RequestMethod.POST)
    StringResponse addMenu(MenuRequest request);

    /**
     * 编辑菜单
     * @param request
     * @return
     */
    @RequestMapping(value = "/editMenu", method = RequestMethod.POST)
    StringResponse editMenu(MenuRequest request);

    /**
     * 删除菜单
     * @param request
     * @return
     */
    @RequestMapping(value = "/removeMenu", method = RequestMethod.POST)
    NormalResponse removeMenu(StringRequest request);
}
