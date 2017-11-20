package com.lmtech.infrastructure.facade;

import com.lmtech.common.ContextManager;
import com.lmtech.common.PageData;
import com.lmtech.facade.request.NormalRequest;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import com.lmtech.infrastructure.facade.request.*;
import com.lmtech.infrastructure.facade.response.*;
import com.lmtech.infrastructure.facade.stub.UserFacade;
import com.lmtech.infrastructure.model.GroupUser;
import com.lmtech.infrastructure.model.Role;
import com.lmtech.infrastructure.model.User;
import com.lmtech.infrastructure.service.GroupManager;
import com.lmtech.infrastructure.service.GroupService;
import com.lmtech.infrastructure.service.UserManager;
import com.lmtech.infrastructure.service.UserService;
import com.lmtech.infrastructure.vo.UserInfo;
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
 * Created by huang.jb on 2017-2-10.
 */
@RestController
@RequestMapping(value = "/user")
@Api(description = "用户API入口")
public class UserFacadeImpl implements UserFacade {

    @Autowired
    private GroupService groupService;
    @Autowired
    private GroupManager groupManager;
    @Autowired
    private UserService userService;
    @Autowired
    private UserManager userManager;

    @Override
    @RequestMapping(value = "/queryCurrentUserInfo", method = RequestMethod.POST)
    @ApiOperation(value = "查询当前用户信息")
    public UserInfoResponse queryCurrentUserInfo(@RequestBody NormalRequest request) {
        String account = ContextManager.getContext().getLoginName();

        UserInfo userInfo = userService.getUserInfoByAccount(account);

        UserInfoResponse response = new UserInfoResponse();
        response.setSuccess(true);
        response.setData(userInfo);
        response.setMessage("获取当前用户信息成功。");
        return response;
    }

    @Override
    @RequestMapping(value = "/queryUserInfoInGroup", method = RequestMethod.POST)
    @ApiOperation(value = "查询分组下的用户")
    public UserInfoListResponse queryUserInfoInGroup(@RequestBody StringRequest request) {
        String groupId = request.getReqData();

        List<User> userList = groupService.queryGroupUsers(groupId);
        List<UserInfo> userInfoList = new ArrayList<UserInfo>();
        if (!CollectionUtil.isNullOrEmpty(userList)) {
            for (User user : userList) {
                UserInfo ui = new UserInfo();
                ui.setUserId(user.getId());
                ui.setUserName(user.getName());
                userInfoList.add(ui);
            }
        }

        UserInfoListResponse response = new UserInfoListResponse();
        response.setSuccess(true);
        response.setData(userInfoList);
        response.setMessage("查询分组下的用户基础信息成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/queryUserByGroupId", method = RequestMethod.POST)
    @ApiOperation(value = "查询用户通过分组编号")
    public UserListResponse queryUserByGroupId(@RequestBody StringRequest request) {
        String groupId = request.getReqData();

        List<User> userList = groupService.queryGroupUsers(groupId);

        UserListResponse response = new UserListResponse();
        response.setData(userList);
        response.setSuccess(true);
        response.setMessage("查询分组下的用户数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/queryUserByKey", method = RequestMethod.POST)
    @ApiOperation(value = "查询用户通过关键字（如：用户名、登录名）")
    public UserListResponse queryUserByKey(@RequestBody StringRequest request) {
        String key = request.getReqData();

        List<User> userList = userService.getUserByKey(key);

        UserListResponse response = new UserListResponse();
        response.setData(userList);
        response.setSuccess(true);
        response.setMessage("查询关键字用户数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/queryUserRoles", method = RequestMethod.POST)
    @ApiOperation(value = "查询用户关联角色")
    public RoleListResponse queryUserRoles(@RequestBody StringRequest request) {
        String userId = request.getReqData();

        List<Role> roles = userService.getUserRoles(userId);

        RoleListResponse response = new RoleListResponse();
        response.setSuccess(true);
        response.setData(roles);
        response.setMessage("查询用户角色关联数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/addGroupUser", method = RequestMethod.POST)
    @ApiOperation(value = "添加用户分组关联")
    public NormalResponse addGroupUser(@RequestBody GroupUserRequest request) {
        GroupUser groupUser = request.getReqData();

        List<String> userIds = new ArrayList<String>();
        userIds.add(groupUser.getUserId());
        groupManager.addGroupUsers(groupUser.getGroupId(), userIds);

        NormalResponse response = new NormalResponse();
        response.setSuccess(true);
        response.setMessage("添加分组用户关联成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/getUserByGroupId", method = RequestMethod.POST)
    @ApiOperation(value = "获取用户通过分组编号")
    public UserListResponse getUserByGroupId(@RequestBody StringRequest request) {
        String groupId = request.getReqData();

        List<User> userList = groupManager.getGroupUsers(groupId);

        UserListResponse response = new UserListResponse();
        response.setData(userList);
        response.setSuccess(true);
        response.setMessage("获取分组下的用户数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/changeGroup", method = RequestMethod.POST)
    @ApiOperation(value = "修改分组")
    public NormalResponse changeGroup(@RequestBody ChangeGroupRequest request) {
        return null;
    }

    @Override
    @RequestMapping(value = "/getUserRoleOfPage", method = RequestMethod.POST)
    @ApiOperation(value = "获取用户关联角色分页数据")
    public RolePageResponse getUserRoleOfPage(@RequestBody UserRolePageRequest request) {
        String userId = request.getUserId();
        Map<Object, Object> param = new HashMap<Object, Object>();
        int pageIndex = request.getPageIndex();
        int pageSize = request.getPageSize();

        PageData<Role> pageData = userManager.getUserRoles(userId, param, pageIndex, pageSize);

        RolePageResponse response = new RolePageResponse();
        response.setSuccess(true);
        response.setData(pageData);
        response.setMessage("获取用户角色关联分页数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/getUserUnauthRoleOfPage", method = RequestMethod.POST)
    @ApiOperation(value = "获取用户未关联角色分页数据")
    public RolePageResponse getUserUnauthRoleOfPage(@RequestBody UserRolePageRequest request) {
        String userId = request.getUserId();
        Map<Object, Object> param = new HashMap<Object, Object>();
        int pageIndex = request.getPageIndex();
        int pageSize = request.getPageSize();

        PageData<Role> pageData = userManager.getUserUnauthRoles(userId, param, pageIndex, pageSize);

        RolePageResponse response = new RolePageResponse();
        response.setSuccess(true);
        response.setData(pageData);
        response.setMessage("获取用户角色未关联分页数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/setUserRoles", method = RequestMethod.POST)
    @ApiOperation(value = "设置用户关联角色")
    public NormalResponse setUserRoles(@RequestBody UserAndRoleIdsRequest request) {
        String userId = request.getUserId();
        List<String> roleIds = request.getRoleIds();

        userManager.setUserRoles(userId, roleIds);

        NormalResponse response = new NormalResponse();
        response.setSuccess(true);
        response.setMessage("设置用户角色关联关系成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/addUserRoles", method = RequestMethod.POST)
    @ApiOperation(value = "添加用户关联角色")
    public NormalResponse addUserRoles(@RequestBody UserAndRoleIdsRequest request) {
        String userId = request.getUserId();
        List<String> roleIds = request.getRoleIds();

        userManager.addUserRoles(userId, roleIds);

        NormalResponse response = new NormalResponse();
        response.setSuccess(true);
        response.setMessage("添加用户角色关联关系成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/removeUserRoles", method = RequestMethod.POST)
    @ApiOperation(value = "删除用户关联角色")
    public NormalResponse removeUserRoles(@RequestBody UserAndRoleIdsRequest request) {
        String userId = request.getUserId();
        List<String> roleIds = request.getRoleIds();

        userManager.removeUserRoles(userId, roleIds);

        NormalResponse response = new NormalResponse();
        response.setSuccess(true);
        response.setMessage("删除用户角色关联关系成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/getUser", method = RequestMethod.POST)
    @ApiOperation(value = "获取用户")
    public UserResponse getUser(@RequestBody StringRequest request) {
        String id = request.getReqData();

        User user = userManager.get(id);

        UserResponse response = new UserResponse();
        response.setData(user);
        response.setSuccess(true);
        response.setMessage("获取用户数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/getUserOfPage", method = RequestMethod.POST)
    @ApiOperation(value = "获取用户分页数据")
    public UserPageResponse getUserOfPage(@RequestBody UserPageRequest request) {
        PageData<User> pageData = userManager.getPageData(request.getPageParam(), request.getPageIndex(), request.getPageSize());

        UserPageResponse response = new UserPageResponse();
        response.setSuccess(true);
        response.setData(pageData);
        response.setMessage("获取用户分页数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    @ApiOperation(value = "添加用户")
    public StringResponse addUser(@RequestBody UserRequest request) {
        User user = request.getReqData();

        String id = userManager.add(user);

        StringResponse response = new StringResponse(id);
        response.setMessage("添加用户成功");
        response.setSuccess(true);
        return response;
    }

    @Override
    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
    @ApiOperation(value = "编辑用户")
    public StringResponse editUser(@RequestBody UserRequest request) {
        User user = request.getReqData();
        Assert.notNull(user.getId(), "传入用户id不允许为空");

        userManager.edit(user);

        StringResponse response = new StringResponse();
        response.setMessage("编辑用户成功");
        response.setSuccess(true);
        return response;
    }

    @Override
    @RequestMapping(value = "/removeUser", method = RequestMethod.POST)
    @ApiOperation(value = "删除用户")
    public NormalResponse removeUser(@RequestBody StringRequest request) {
        String id = request.getReqData();

        userManager.remove(id);

        NormalResponse response = new NormalResponse();
        response.setMessage("删除用户成功");
        response.setSuccess(true);
        return response;
    }
}
