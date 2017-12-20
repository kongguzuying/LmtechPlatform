package com.lmtech.infrastructure.facade.request;

import com.lmtech.facade.request.PageRequest;
import com.lmtech.infrastructure.model.Resource;

/**
 * 角色关联资源分页请求
 * Created by huang.jb on 2017-4-21.
 */
public class RoleResourcePgaeRequest extends PageRequest<Resource> {

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
