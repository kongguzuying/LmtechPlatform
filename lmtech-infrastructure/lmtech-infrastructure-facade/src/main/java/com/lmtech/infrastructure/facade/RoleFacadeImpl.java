package com.lmtech.infrastructure.facade;

import com.lmtech.common.ContextManager;
import com.lmtech.common.PageData;
import com.lmtech.facade.request.NormalRequest;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.BooleanResponse;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import com.lmtech.infrastructure.facade.request.*;
import com.lmtech.infrastructure.facade.response.*;
import com.lmtech.infrastructure.facade.stub.RoleFacade;
import com.lmtech.infrastructure.model.*;
import com.lmtech.infrastructure.service.RoleManager;
import com.lmtech.infrastructure.service.RoleService;
import com.lmtech.infrastructure.service.UserService;
import com.lmtech.util.CollectionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huang.jb on 2017-3-29.
 */
@RestController
@RequestMapping(value = "/role")
@Api(description = "角色API入口")
public class RoleFacadeImpl implements RoleFacade {

    @Autowired
    private RoleManager roleManager;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

    @Override
    @RequestMapping(value = "/queryCurrentUserRole", method = RequestMethod.POST)
    @ApiOperation(value = "获取当前用户角色列表")
    public RoleListResponse queryCurrentUserRole(@RequestBody NormalRequest request) {
        String userId = ContextManager.getContext().getUserId();
        Assert.notNull(userId, "当前用户登录信息不存在");

        List<Role> roles;
        if (userService.isSuperAdmin(userId)) {
            roles = roleService.getAllValidRole();
        } else {
            roles = userService.getUserRoles(userId);
        }

        RoleListResponse response = new RoleListResponse();
        response.setData(roles);
        response.setSuccess(true);
        response.setMessage("查询当前用户相关联角色成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/queryUserRoleByUserId", method = RequestMethod.POST)
    @ApiOperation(value = "通过用户编号获取用户角色关联")
    public UserRoleListResponse queryUserRoleByUserId(@RequestBody StringRequest request) {
        String userId = request.getReqData();

        List<Role> roles = userService.getUserRoles(userId);
        List<UserRole> userRoles = new ArrayList<UserRole>();
        if (!CollectionUtil.isNullOrEmpty(roles)) {
            for (Role role : roles) {
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(role.getId());
                userRoles.add(userRole);
            }
        }

        UserRoleListResponse response = new UserRoleListResponse();
        response.setData(userRoles);
        response.setSuccess(true);
        response.setMessage("获取用户角色关联关系成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/existRoleName", method = RequestMethod.POST)
    @ApiOperation(value = "是否已存在角色名称")
    public BooleanResponse existRoleName(@RequestBody StringRequest request) {
        String roleName = request.getReqData();

        boolean exist = roleService.existRoleName(roleName);

        BooleanResponse response = new BooleanResponse(exist);
        response.setSuccess(true);
        response.setMessage("查询是否存在相同的角色名称成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/getRole", method = RequestMethod.POST)
    @ApiOperation(value = "获取角色数据")
    public RoleResponse getRole(@RequestBody StringRequest request) {
        String id = request.getReqData();

        Role role = roleManager.get(id);

        RoleResponse response = new RoleResponse();
        response.setData(role);
        response.setSuccess(true);
        response.setMessage("获取角色数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/getRoleOfPage", method = RequestMethod.POST)
    @ApiOperation(value = "获取角色分页数据")
    public RolePageResponse getRoleOfPage(@RequestBody RolePageRequest request) {
        PageData<Role> pageData = roleManager.getPageData(request.getPageParam(), request.getPageIndex(), request.getPageSize());

        RolePageResponse response = new RolePageResponse();
        response.setSuccess(true);
        response.setData(pageData);
        response.setMessage("获取角色分页数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    @ApiOperation(value = "添加角色")
    public StringResponse addRole(@RequestBody RoleRequest request) {
        Role role = request.getReqData();

        String id = roleManager.add(role);

        StringResponse response = new StringResponse(id);
        response.setSuccess(true);
        response.setMessage("添加角色成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/editRole", method = RequestMethod.POST)
    @ApiOperation(value = "编辑角色")
    public StringResponse editRole(@RequestBody RoleRequest request) {
        Role role = request.getReqData();
        Assert.notNull(role.getId(), "传入角色id不允许为空");

        roleManager.edit(role);

        StringResponse response = new StringResponse();
        response.setSuccess(true);
        response.setMessage("编辑角色成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/removeRole", method = RequestMethod.POST)
    @ApiOperation(value = "删除角色")
    public NormalResponse removeRole(@RequestBody StringRequest request) {
        String id = request.getReqData();

        roleManager.remove(id);

        NormalResponse response = new NormalResponse();
        response.setSuccess(true);
        response.setMessage("删除角色成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/getRoleMenus", method = RequestMethod.POST)
    @ApiOperation(value = "获取角色相关菜单")
    public MenuListResponse getRoleMenus(@RequestBody StringRequest request) {
        String roleId = request.getReqData();

        List<Menu> menus = roleManager.getRoleMenus(roleId);

        MenuListResponse response = new MenuListResponse();
        response.setData(menus);
        response.setSuccess(true);
        response.setMessage("获取角色关联菜单成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/getRoleUnauthMenus", method = RequestMethod.POST)
    @ApiOperation(value = "获取角色相关菜单")
    public MenuListResponse getRoleUnauthMenus(@RequestBody StringRequest request) {
        String roleId = request.getReqData();

        List<Menu> menus = roleManager.getRoleUnauthMenus(roleId);

        MenuListResponse response = new MenuListResponse();
        response.setData(menus);
        response.setSuccess(true);
        response.setMessage("获取角色未关联菜单成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/setRoleMenus", method = RequestMethod.POST)
    @ApiOperation(value = "设置角色菜单")
    public NormalResponse setRoleMenus(@RequestBody RoleAndAuthIdsRequest request) {
        String roleId = request.getRoleId();
        List<String> menuIds = request.getAuthIds();

        roleManager.setRoleMenus(roleId, menuIds);

        NormalResponse response = new NormalResponse();
        response.setSuccess(true);
        response.setMessage("设置角色关联菜单成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/addRoleMenus", method = RequestMethod.POST)
    @ApiOperation(value = "添加角色菜单")
    public NormalResponse addRoleMenus(@RequestBody RoleAndAuthIdsRequest request) {
        String roleId = request.getRoleId();
        List<String> menuIds = request.getAuthIds();

        roleManager.addRoleMenus(roleId, menuIds);

        NormalResponse response = new NormalResponse();
        response.setSuccess(true);
        response.setMessage("添加角色关联菜单成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/removeRoleMenus", method = RequestMethod.POST)
    @ApiOperation(value = "删除角色菜单")
    public NormalResponse removeRoleMenus(@RequestBody RoleAndAuthIdsRequest request) {
        String roleId = request.getRoleId();
        List<String> menuIds = request.getAuthIds();

        roleManager.removeRoleMenus(roleId, menuIds);

        NormalResponse response = new NormalResponse();
        response.setSuccess(true);
        response.setMessage("删除角色关联菜单成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/getRoleUsers", method = RequestMethod.POST)
    @ApiOperation(value = "获取角色关联用户")
    public UserListResponse getRoleUsers(@RequestBody StringRequest request) {
        String roleId = request.getReqData();

        List<User> users = roleService.getRoleUsers(roleId);

        UserListResponse response = new UserListResponse();
        response.setSuccess(true);
        response.setData(users);
        response.setMessage("获取角色关联用户数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/getRoleUserOfPage", method = RequestMethod.POST)
    @ApiOperation(value = "获取角色关联用户分页数据")
    public UserPageResponse getRoleUserOfPage(@RequestBody RoleUserPageRequest request) {
        PageData<User> pageData = roleManager.getRoleUsers(request.getRoleId(), null, request.getPageIndex(), request.getPageSize());

        UserPageResponse response = new UserPageResponse();
        response.setSuccess(true);
        response.setData(pageData);
        response.setMessage("获取角色关联用户分页数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/getRoleUnauthUserOfPage", method = RequestMethod.POST)
    @ApiOperation(value = "获取角色未关联用户")
    public UserPageResponse getRoleUnauthUserOfPage(@RequestBody RoleUserPageRequest request) {
        PageData<User> pageData = roleManager.getRoleUnauthUsers(request.getRoleId(), null, request.getPageIndex(), request.getPageSize());

        UserPageResponse response = new UserPageResponse();
        response.setSuccess(true);
        response.setData(pageData);
        response.setMessage("获取角色未关联用户分页数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/setRoleUsers", method = RequestMethod.POST)
    @ApiOperation(value = "设置角色用户关联")
    public NormalResponse setRoleUsers(@RequestBody RoleAndAuthIdsRequest request) {
        String roleId = request.getRoleId();
        List<String> userIds = request.getAuthIds();

        roleManager.setRoleUsers(roleId, userIds);

        NormalResponse response = new NormalResponse();
        response.setSuccess(true);
        response.setMessage("设置角色关联用户成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/addRoleUsers", method = RequestMethod.POST)
    @ApiOperation(value = "添加角色用户关联")
    public NormalResponse addRoleUsers(@RequestBody RoleAndAuthIdsRequest request) {
        String roleId = request.getRoleId();
        List<String> userIds = request.getAuthIds();

        roleManager.addRoleUsers(roleId, userIds);

        NormalResponse response = new NormalResponse();
        response.setSuccess(true);
        response.setMessage("添加角色关联用户成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/removeRoleUsers", method = RequestMethod.POST)
    @ApiOperation(value = "删除角色用户关联")
    public NormalResponse removeRoleUsers(@RequestBody RoleAndAuthIdsRequest request) {
        String roleId = request.getRoleId();
        List<String> userIds = request.getAuthIds();

        roleManager.removeRoleUsers(roleId, userIds);

        NormalResponse response = new NormalResponse();
        response.setSuccess(true);
        response.setMessage("删除角色关联用户成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/getRoleResources", method = RequestMethod.POST)
    @ApiOperation(value = "获取角色关联资源")
    public ResourceListResponse getRoleResources(@RequestBody StringRequest request) {
        String roleId = request.getReqData();

        List<Resource> resources = roleService.getRoleResources(roleId);

        ResourceListResponse response = new ResourceListResponse();
        response.setSuccess(true);
        response.setData(resources);
        response.setMessage("获取角色关联资源成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/getRoleResourceOfPage", method = RequestMethod.POST)
    @ApiOperation(value = "获取角色关联资源分页数据")
    public ResourcePageResponse getRoleResourceOfPage(@RequestBody RoleResourcePgaeRequest request) {
        String roleId = request.getRoleId();
        int pageIndex = request.getPageIndex();
        int pageSize = request.getPageSize();

        PageData<Resource> pageData = roleManager.getRoleResources(roleId, null, pageIndex, pageSize);

        ResourcePageResponse response = new ResourcePageResponse();
        response.setSuccess(true);
        response.setData(pageData);
        response.setMessage("获取角色关联资源分页数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/getRoleUnauthResourceOfPage", method = RequestMethod.POST)
    @ApiOperation(value = "获取角色未关联资源")
    public ResourcePageResponse getRoleUnauthResourceOfPage(@RequestBody RoleResourcePgaeRequest request) {
        String roleId = request.getRoleId();
        int pageIndex = request.getPageIndex();
        int pageSize = request.getPageSize();

        PageData<Resource> pageData = roleManager.getRoleUnauthResources(roleId, null, pageIndex, pageSize);

        ResourcePageResponse response = new ResourcePageResponse();
        response.setSuccess(true);
        response.setData(pageData);
        response.setMessage("获取角色未关联资源分页数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/setRoleResources", method = RequestMethod.POST)
    @ApiOperation(value = "设置角色资源关联")
    public NormalResponse setRoleResources(@RequestBody RoleAndAuthIdsRequest request) {
        return null;
    }

    @Override
    @RequestMapping(value = "/addRoleResources", method = RequestMethod.POST)
    @ApiOperation(value = "添加角色资源关联")
    public NormalResponse addRoleResources(@RequestBody RoleAndAuthIdsRequest request) {
        return null;
    }

    @Override
    @RequestMapping(value = "/removeRoleResources", method = RequestMethod.POST)
    @ApiOperation(value = "删除角色资源关联")
    public NormalResponse removeRoleResources(@RequestBody RoleAndAuthIdsRequest request) {
        return null;
    }
}
