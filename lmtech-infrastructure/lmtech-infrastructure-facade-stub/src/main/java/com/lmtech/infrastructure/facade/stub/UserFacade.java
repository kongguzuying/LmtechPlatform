package com.lmtech.infrastructure.facade.stub;

import com.lmtech.facade.request.NormalRequest;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import com.lmtech.infrastructure.facade.request.*;
import com.lmtech.infrastructure.facade.response.*;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 用户服务入口
 * Created by huang.jb on 2017-2-10.
 */
@FeignClient(name = "lmtech-infrastructure")
@RequestMapping(value = "/user")
public interface UserFacade {
    /**
     * 查询当前用户信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryCurrentUserInfo", method = RequestMethod.POST)
    UserInfoResponse queryCurrentUserInfo(NormalRequest request);

    /**
     * 查询分组下的用户
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryUserInfoInGroup", method = RequestMethod.POST)
    UserInfoListResponse queryUserInfoInGroup(StringRequest request);

    /**
     * 查询用户通过分组编号
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryUserByGroupId", method = RequestMethod.POST)
    UserListResponse queryUserByGroupId(StringRequest request);

    /**
     * 查询用户通过关键字（如：用户名、登录名）
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryUserByKey", method = RequestMethod.POST)
    UserListResponse queryUserByKey(StringRequest request);

    /**
     * 查询用户关联角色
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryUserRoles", method = RequestMethod.POST)
    RoleListResponse queryUserRoles(StringRequest request);

    /**
     * 添加用户分组关联
     * @param request
     */
    @RequestMapping(value = "/addGroupUser", method = RequestMethod.POST)
    NormalResponse addGroupUser(GroupUserRequest request);

    /**
     * 获取用户通过分组编号
     * @param request
     * @return
     */
    @RequestMapping(value = "/getUserByGroupId", method = RequestMethod.POST)
    UserListResponse getUserByGroupId(StringRequest request);

    /**
     * 修改分组
     * @param request
     */
    @RequestMapping(value = "/changeGroup", method = RequestMethod.POST)
    NormalResponse changeGroup(ChangeGroupRequest request);

    /**
     * 获取用户关联角色分页数据
     * @param request
     * @return
     */
    @RequestMapping(value = "/getUserRoleOfPage", method = RequestMethod.POST)
    RolePageResponse getUserRoleOfPage(UserRolePageRequest request);

    /**
     * 获取用户未关联角色分页数据
     * @param request
     * @return
     */
    @RequestMapping(value = "/getUserUnauthRoleOfPage", method = RequestMethod.POST)
    RolePageResponse getUserUnauthRoleOfPage(UserRolePageRequest request);

    /**
     * 设置用户关联角色
     * @param request
     */
    @RequestMapping(value = "/setUserRoles", method = RequestMethod.POST)
    NormalResponse setUserRoles(UserAndRoleIdsRequest request);

    /**
     * 添加用户关联角色
     * @param request
     */
    @RequestMapping(value = "/addUserRoles", method = RequestMethod.POST)
    NormalResponse addUserRoles(UserAndRoleIdsRequest request);

    /**
     * 删除用户关联角色
     * @param request
     */
    @RequestMapping(value = "/removeUserRoles", method = RequestMethod.POST)
    NormalResponse removeUserRoles(UserAndRoleIdsRequest request);

    /**
     * 获取用户
     * @param request
     * @return
     */
    @RequestMapping(value = "/getUser", method = RequestMethod.POST)
    UserResponse getUser(StringRequest request);

    /**
     * 获取用户分页数据
     * @param request
     * @return
     */
    @RequestMapping(value = "/getUserOfPage", method = RequestMethod.POST)
    UserPageResponse getUserOfPage(UserPageRequest request);

    /**
     * 添加用户
     * @param request
     * @return
     */
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    StringResponse addUser(UserRequest request);

    /**
     * 编辑用户
     * @param request
     * @return
     */
    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
    StringResponse editUser(UserRequest request);

    /**
     * 删除用户
     * @param request
     * @return
     */
    @RequestMapping(value = "/removeUser", method = RequestMethod.POST)
    NormalResponse removeUser(StringRequest request);
}
