package com.lmtech.infrastructure.facade.request;

import com.lmtech.facade.request.PageRequest;
import com.lmtech.infrastructure.model.User;

/**
 * 角色用户分页数据请求
 * Created by huang.jb on 2017-4-10.
 */
public class RoleUserPageRequest extends PageRequest<User> {

    private String roleId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public void validate() {

    }
}
