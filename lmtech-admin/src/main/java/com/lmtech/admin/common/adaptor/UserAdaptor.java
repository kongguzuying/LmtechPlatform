package com.lmtech.admin.common.adaptor;

import com.lmtech.auth.facade.dto.AccountAuthData;
import com.lmtech.auth.facade.dto.AccountRegisterData;
import com.lmtech.auth.facade.request.AccountAuthRequest;
import com.lmtech.auth.facade.request.AccountRegisterRequest;
import com.lmtech.auth.facade.stub.AccountFacade;
import com.lmtech.auth.model.Account;
import com.lmtech.common.ContextManager;
import com.lmtech.common.PageData;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import com.lmtech.infrastructure.facade.dto.UserQueryParam;
import com.lmtech.infrastructure.facade.request.*;
import com.lmtech.infrastructure.facade.response.*;
import com.lmtech.infrastructure.facade.stub.UserFacade;
import com.lmtech.infrastructure.model.GroupUser;
import com.lmtech.infrastructure.model.Role;
import com.lmtech.infrastructure.model.User;
import com.lmtech.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户服务适配器
 * Created by huang.jb on 2017-3-29.
 */
@Service
public class UserAdaptor extends ServiceAdaptorBase implements ControllerManager<User> {

    @Autowired
    private UserFacade userFacade;
    @Autowired
    private AccountFacade accountFacade;

    /**
     * 获取用户关联角色
     * @param userId
     * @return
     */
    public List<Role> getUserRoles(String userId) {
        StringRequest request = new StringRequest(userId);
        initRequest(request);

        RoleListResponse response = userFacade.queryUserRoles(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public String add(User user) {
        UserRequest request = new UserRequest();
        request.setReqData(user);
        initRequest(request);

        StringResponse response = userFacade.addUser(request);

        validResponse(response);

        //添加帐户
        String loginName = ContextManager.getValue("userLoginName");
        Account account = new Account();
        account.setLoginName(loginName);
        account.setUserId(response.getData());
        account.setPassword(MD5Util.getMD5String("123456"));
        account.setName(loginName);
        account.setEnable(true);

        registerAccount(account);

        return response.getData();
    }

    private void registerAccount(Account account) {
        AccountRegisterRequest request = new AccountRegisterRequest();
        AccountRegisterData data = new AccountRegisterData();
        data.setLoginName(account.getLoginName());
        data.setPassword(account.getPassword());
        request.setReqData(data);
        initRequest(request);

        NormalResponse response = accountFacade.register(request);

        validResponse(response);
    }

    @Override
    public String edit(User user) {
        UserRequest request = new UserRequest();
        user.increaseDataVersion();
        request.setReqData(user);
        initRequest(request);

        StringResponse response = userFacade.editUser(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public void remove(String id) {
        StringRequest request = new StringRequest(id);
        initRequest(request);

        NormalResponse response = userFacade.removeUser(request);
        validResponse(response);
    }

    @Override
    public User get(String id) {
        StringRequest request = new StringRequest(id);
        initRequest(request);

        UserResponse response = userFacade.getUser(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public PageData<User> getPageData(User param, int pageIndex, int pageSize) {
        UserPageRequest request = new UserPageRequest();
        UserQueryParam queryParam = new UserQueryParam();
        queryParam.setNickName(param.getNickName());
        queryParam.setEmail(param.getEmail());
        queryParam.setMobile(param.getMobile());
        queryParam.setSex(param.getSex());
        request.setReqData(queryParam);
        request.setPageIndex(pageIndex);
        request.setPageSize(pageSize);
        initRequest(request);

        UserPageResponse response = userFacade.getUserOfPage(request);
        validResponse(response);
        return response.getData();
    }

    /**
     * 获取用户通过群组id
     * @param groupId
     * @return
     */
    public List<User> getUserByGroupId(String groupId) {
        StringRequest request = new StringRequest(groupId);
        initRequest(request);

        UserListResponse response = userFacade.getUserByGroupId(request);
        validResponse(response);
        return response.getData();
    }

    /**
     * 获取用户通过关键字
     * @param key
     * @return
     */
    public List<User> getUserByKey(String key) {
        StringRequest request = new StringRequest(key);
        initRequest(request);

        UserListResponse response = userFacade.queryUserByKey(request);
        validResponse(response);
        return response.getData();
    }

    /**
     * 修改群组
     * @param userId
     * @param oldGrouopId
     * @param newGroupId
     */
    public void changeGroup(String userId, String oldGrouopId, String newGroupId) {
        //TODO
    }

    /**
     * 当前用户是否管理员
     * @return
     */
    public boolean isAdminCurrentUser() {
        //TODO
        return false;
    }

    /**
     * 当前用户是否超级管理员
     * @return
     */
    public boolean isSuperAdminCurrentUser() {
        //TODO
        return false;
    }

    /**
     * 获取用户关联角色分页数据
     * @param userId
     * @param param
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public PageData<Role> getUserRoleOfPage(String userId, Role param, int pageIndex, int pageSize) {
        UserRolePageRequest request = new UserRolePageRequest();
        request.setUserId(userId);
        request.setReqData(param);
        request.setPageIndex(pageIndex);
        request.setPageSize(pageSize);
        initRequest(request);

        RolePageResponse response = userFacade.getUserRoleOfPage(request);
        validResponse(response);
        return response.getData();
    }

    /**
     * 获取用户未关联角色分页数据
     * @param userId
     * @param param
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public PageData<Role> getUserUnauthRoleOfPage(String userId, Role param, int pageIndex, int pageSize) {
        UserRolePageRequest request = new UserRolePageRequest();
        request.setUserId(userId);
        request.setReqData(param);
        request.setPageIndex(pageIndex);
        request.setPageSize(pageSize);
        initRequest(request);

        RolePageResponse response = userFacade.getUserUnauthRoleOfPage(request);
        validResponse(response);
        return response.getData();
    }

    /**
     * 设置用户关联角色
     * @param userId
     * @param roleIds
     */
    public void setUserRoles(String userId, List<String> roleIds) {
        UserAndRoleIdsRequest request = new UserAndRoleIdsRequest();
        request.setUserId(userId);
        request.setRoleIds(roleIds);
        initRequest(request);

        NormalResponse response = userFacade.setUserRoles(request);
        validResponse(response);
    }

    /**
     * 添加用户关联角色
     * @param userId
     * @param roleIds
     */
    public void addUserRoles(String userId, List<String> roleIds) {
        UserAndRoleIdsRequest request = new UserAndRoleIdsRequest();
        request.setUserId(userId);
        request.setRoleIds(roleIds);
        initRequest(request);

        NormalResponse response = userFacade.addUserRoles(request);
        validResponse(response);
    }

    /**
     * 删除用户关联角色
     * @param userId
     * @param roleIds
     */
    public void removeUserRoles(String userId, List<String> roleIds) {
        UserAndRoleIdsRequest request = new UserAndRoleIdsRequest();
        request.setUserId(userId);
        request.setRoleIds(roleIds);
        initRequest(request);

        NormalResponse response = userFacade.removeUserRoles(request);
        validResponse(response);
    }

    /**
     * 添加分组用户关联
     * @param groupId
     * @param userId
     */
    public void addGroupUser(String groupId, String userId) {
        GroupUserRequest request = new GroupUserRequest();
        GroupUser groupUser = new GroupUser();
        groupUser.setGroupId(groupId);
        groupUser.setUserId(userId);
        request.setReqData(groupUser);
        initRequest(request);

        NormalResponse response = userFacade.addGroupUser(request);
        validResponse(response);
    }
}
