package com.lmtech.infrastructure.facade.stub;

import com.lmtech.facade.request.NormalRequest;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.BooleanResponse;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import com.lmtech.infrastructure.facade.request.*;
import com.lmtech.infrastructure.facade.response.*;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 角色服务入口
 * Created by huang.jb on 2017-3-29.
 */
@FeignClient(name = "lmtech-infrastructure")
@RequestMapping(value = "/role")
public interface RoleFacade {
    /**
     * 获取当前用户角色列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryCurrentUserRole", method = RequestMethod.POST)
    RoleListResponse queryCurrentUserRole(NormalRequest request);

    /**
     * 通过用户编号获取用户角色关联
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryUserRoleByUserId", method = RequestMethod.POST)
    UserRoleListResponse queryUserRoleByUserId(StringRequest request);

    /**
     * 是否已存在角色名称
     * @param request
     * @return
     */
    @RequestMapping(value = "/existRoleName", method = RequestMethod.POST)
    BooleanResponse existRoleName(StringRequest request);

    /**
     * 获取角色数据
     * @param request
     * @return
     */
    @RequestMapping(value = "/getRole", method = RequestMethod.POST)
    RoleResponse getRole(StringRequest request);

    /**
     * 获取角色分页数据
     * @param request
     * @return
     */
    @RequestMapping(value = "/getRoleOfPage", method = RequestMethod.POST)
    RolePageResponse getRoleOfPage(RolePageRequest request);

    /**
     * 添加角色
     * @param request
     * @return
     */
    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    StringResponse addRole(RoleRequest request);

    /**
     * 编辑角色
     * @param request
     * @return
     */
    @RequestMapping(value = "/editRole", method = RequestMethod.POST)
    StringResponse editRole(RoleRequest request);

    /**
     * 删除角色
     * @param request
     * @return
     */
    @RequestMapping(value = "/removeRole", method = RequestMethod.POST)
    NormalResponse removeRole(StringRequest request);

    /**
     * 获取角色相关菜单
     * @param request
     * @return
     */
    @RequestMapping(value = "/getRoleMenus", method = RequestMethod.POST)
    MenuListResponse getRoleMenus(StringRequest request);

    /**
     * 获取角色相关菜单
     * @param request
     * @return
     */
    @RequestMapping(value = "/getRoleUnauthMenus", method = RequestMethod.POST)
    MenuListResponse getRoleUnauthMenus(StringRequest request);

    /**
     * 设置角色菜单
     * @param request
     */
    @RequestMapping(value = "/setRoleMenus", method = RequestMethod.POST)
    NormalResponse setRoleMenus(RoleAndAuthIdsRequest request);

    /**
     * 添加角色菜单
     * @param request
     */
    @RequestMapping(value = "/addRoleMenus", method = RequestMethod.POST)
    NormalResponse addRoleMenus(RoleAndAuthIdsRequest request);

    /**
     * 删除角色菜单
     * @param request
     */
    @RequestMapping(value = "/removeRoleMenus", method = RequestMethod.POST)
    NormalResponse removeRoleMenus(RoleAndAuthIdsRequest request);

    /**
     * 获取角色关联用户
     * @param request
     * @return
     */
    @RequestMapping(value = "/getRoleUsers", method = RequestMethod.POST)
    UserListResponse getRoleUsers(StringRequest request);

    /**
     * 获取角色关联用户分页数据
     * @param request
     * @return
     */
    @RequestMapping(value = "/getRoleUserOfPage", method = RequestMethod.POST)
    UserPageResponse getRoleUserOfPage(RoleUserPageRequest request);

    /**
     * 获取角色未关联用户
     * @param request
     * @return
     */
    @RequestMapping(value = "/getRoleUnauthUserOfPage", method = RequestMethod.POST)
    UserPageResponse getRoleUnauthUserOfPage(RoleUserPageRequest request);

    /**
     * 设置角色用户关联
     * @param request
     */
    @RequestMapping(value = "/setRoleUsers", method = RequestMethod.POST)
    NormalResponse setRoleUsers(RoleAndAuthIdsRequest request);

    /**
     * 添加角色用户关联
     * @param request
     */
    @RequestMapping(value = "/addRoleUsers", method = RequestMethod.POST)
    NormalResponse addRoleUsers(RoleAndAuthIdsRequest request);

    /**
     * 删除角色用户关联
     * @param request
     */
    @RequestMapping(value = "/removeRoleUsers", method = RequestMethod.POST)
    NormalResponse removeRoleUsers(RoleAndAuthIdsRequest request);

    /**
     * 获取角色关联资源
     * @param request
     * @return
     */
    @RequestMapping(value = "/getRoleResources", method = RequestMethod.POST)
    ResourceListResponse getRoleResources(StringRequest request);

    /**
     * 获取角色关联资源分页数据
     * @param request
     * @return
     */
    @RequestMapping(value = "/getRoleResourceOfPage", method = RequestMethod.POST)
    ResourcePageResponse getRoleResourceOfPage(RoleResourcePgaeRequest request);

    /**
     * 获取角色未关联资源
     * @param request
     * @return
     */
    @RequestMapping(value = "/getRoleUnauthResourceOfPage", method = RequestMethod.POST)
    ResourcePageResponse getRoleUnauthResourceOfPage(RoleResourcePgaeRequest request);

    /**
     * 设置角色资源关联
     * @param request
     */
    @RequestMapping(value = "/setRoleResources", method = RequestMethod.POST)
    NormalResponse setRoleResources(RoleAndAuthIdsRequest request);

    /**
     * 添加角色资源关联
     * @param request
     */
    @RequestMapping(value = "/addRoleResources", method = RequestMethod.POST)
    NormalResponse addRoleResources(RoleAndAuthIdsRequest request);

    /**
     * 删除角色资源关联
     * @param request
     */
    @RequestMapping(value = "/removeRoleResources", method = RequestMethod.POST)
    NormalResponse removeRoleResources(RoleAndAuthIdsRequest request);
}
