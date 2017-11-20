package com.lmtech.admin.common.adaptor;

import com.lmtech.common.PageData;
import com.lmtech.facade.request.NormalRequest;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.BooleanResponse;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import com.lmtech.infrastructure.facade.request.RoleAndAuthIdsRequest;
import com.lmtech.infrastructure.facade.request.RolePageRequest;
import com.lmtech.infrastructure.facade.request.RoleRequest;
import com.lmtech.infrastructure.facade.request.RoleUserPageRequest;
import com.lmtech.infrastructure.facade.response.*;
import com.lmtech.infrastructure.facade.stub.RoleFacade;
import com.lmtech.infrastructure.model.Menu;
import com.lmtech.infrastructure.model.Role;
import com.lmtech.infrastructure.model.User;
import com.lmtech.infrastructure.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 角色服务适配器
 * Created by huang.jb on 2017-3-29.
 */
@Service
public class RoleAdaptor extends ServiceAdaptorBase implements ControllerManager<Role> {

    @Autowired
    private RoleFacade roleFacade;

    @Override
    public String add(Role role) {
        RoleRequest request = new RoleRequest();
        request.setReqData(role);
        initRequest(request);

        StringResponse response = roleFacade.addRole(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public String edit(Role role) {
        RoleRequest request = new RoleRequest();
        request.setReqData(role);
        initRequest(request);

        StringResponse response = roleFacade.editRole(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public void remove(String id) {
        StringRequest request = new StringRequest(id);
        initRequest(request);

        NormalResponse response = roleFacade.removeRole(request);
        validResponse(response);
    }

    @Override
    public Role get(String id) {
        StringRequest request = new StringRequest(id);
        initRequest(request);

        RoleResponse response = roleFacade.getRole(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public List<Role> getAll() {
        return null;
    }

    @Override
    public PageData getPageData(Role param, int pageIndex, int pageSize) {
        RolePageRequest request = new RolePageRequest();
        request.setPageParam(param);
        request.setPageIndex(pageIndex);
        request.setPageSize(pageSize);
        initRequest(request);

        RolePageResponse response = roleFacade.getRoleOfPage(request);
        validResponse(response);
        return response.getData();
    }

    /**
     * 获取当前用户角色列表
     * @return
     */
    public List<Role> getCurrentUserRoles() {
        NormalRequest request = new NormalRequest();
        initRequest(request);

        RoleListResponse response = roleFacade.queryCurrentUserRole(request);
        validResponse(response);
        return response.getData();
    }

    /**
     * 通过用户编号获取用户角色关联
     * @param userId
     * @return
     */
    public List<UserRole> getUserRoleByUserId(String userId) {
        StringRequest request = new StringRequest(userId);
        initRequest(request);

        UserRoleListResponse response = roleFacade.queryUserRoleByUserId(request);
        validResponse(response);
        return response.getData();
    }

    /**
     * 是否已存在角色名称
     * @param roleName
     * @return
     */
    public boolean existRoleName(String roleName) {
        StringRequest request = new StringRequest(roleName);
        initRequest(request);

        BooleanResponse response = roleFacade.existRoleName(request);
        validResponse(response);
        return response.getData();
    }

    /**
     * 获取角色相关菜单
     * @param roleId
     * @return
     */
    public List<Menu> getRoleMenus(String roleId) {
        StringRequest request = new StringRequest(roleId);
        initRequest(request);

        MenuListResponse response = roleFacade.getRoleMenus(request);
        validResponse(response);
        return response.getData();
    }

    /**
     * 获取角色相关菜单
     * @param roleId
     * @return
     */
    public List<Menu> getRoleUnauthMenus(String roleId) {
        StringRequest request = new StringRequest(roleId);
        initRequest(request);

        MenuListResponse response = roleFacade.getRoleUnauthMenus(request);
        validResponse(response);
        return response.getData();
    }

    /**
     * 设置角色菜单
     * @param roleId
     * @param menuIds
     */
    public void setRoleMenus(String roleId, List<String> menuIds) {
        RoleAndAuthIdsRequest request = new RoleAndAuthIdsRequest();
        request.setRoleId(roleId);
        request.setAuthIds(menuIds);
        initRequest(request);

        NormalResponse response = roleFacade.setRoleMenus(request);
        validResponse(response);
    }

    /**
     * 添加角色菜单
     * @param roleId
     * @param menuIds
     */
    public void addRoleMenus(String roleId, List<String> menuIds) {
        RoleAndAuthIdsRequest request = new RoleAndAuthIdsRequest();
        request.setRoleId(roleId);
        request.setAuthIds(menuIds);
        initRequest(request);

        NormalResponse response = roleFacade.addRoleMenus(request);
        validResponse(response);
    }

    /**
     * 删除角色菜单
     * @param roleId
     * @param menuIds
     */
    public void removeRoleMenus(String roleId, List<String> menuIds) {
        RoleAndAuthIdsRequest request = new RoleAndAuthIdsRequest();
        request.setRoleId(roleId);
        request.setAuthIds(menuIds);
        initRequest(request);

        NormalResponse response = roleFacade.removeRoleMenus(request);
        validResponse(response);
    }

    /**
     * 获取角色关联用户
     * @param roleId
     * @return
     */
    public List<User> getRoleUsers(String roleId) {
        StringRequest request = new StringRequest(roleId);
        initRequest(request);

        UserListResponse response = roleFacade.getRoleUsers(request);
        validResponse(response);
        return response.getData();
    }

    /**
     * 获取角色关联用户分页数据
     * @param roleId
     * @param args
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public PageData<User> getRoleUserOfPage(String roleId, Map<String, Object> args, int pageIndex, int pageSize) {
        RoleUserPageRequest request = new RoleUserPageRequest();
        request.setRoleId(roleId);
        request.setPageParam(null);
        request.setPageIndex(pageIndex);
        request.setPageSize(pageSize);
        initRequest(request);

        UserPageResponse response = roleFacade.getRoleUserOfPage(request);
        validResponse(response);
        return response.getData();
    }

    /**
     * 获取角色未关联用户
     * @param roleId
     * @param args
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public PageData<User> getRoleUnauthUserOfPage(String roleId, Map<String, Object> args, int pageIndex, int pageSize) {
        RoleUserPageRequest request = new RoleUserPageRequest();
        request.setRoleId(roleId);
        request.setPageParam(null);
        request.setPageIndex(pageIndex);
        request.setPageSize(pageSize);
        initRequest(request);

        UserPageResponse response = roleFacade.getRoleUnauthUserOfPage(request);
        validResponse(response);
        return response.getData();
    }

    /**
     * 设置角色用户关联
     * @param roleId
     * @param userIds
     */
    public void setRoleUsers(String roleId, List<String> userIds) {
        RoleAndAuthIdsRequest request = new RoleAndAuthIdsRequest();
        request.setRoleId(roleId);
        request.setAuthIds(userIds);
        initRequest(request);

        NormalResponse response = roleFacade.setRoleUsers(request);
        validResponse(response);
    }

    /**
     * 添加角色用户关联
     * @param roleId
     * @param userIds
     */
    public void addRoleUsers(String roleId, List<String> userIds) {
        RoleAndAuthIdsRequest request = new RoleAndAuthIdsRequest();
        request.setRoleId(roleId);
        request.setAuthIds(userIds);
        initRequest(request);

        NormalResponse response = roleFacade.addRoleUsers(request);
        validResponse(response);
    }

    /**
     * 删除角色用户关联
     * @param roleId
     * @param userIds
     */
    public void removeRoleUsers(String roleId, List<String> userIds) {
        RoleAndAuthIdsRequest request = new RoleAndAuthIdsRequest();
        request.setRoleId(roleId);
        request.setAuthIds(userIds);
        initRequest(request);

        NormalResponse response = roleFacade.removeRoleUsers(request);
        validResponse(response);
    }
}
