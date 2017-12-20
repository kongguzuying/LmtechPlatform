package com.lmtech.infrastructure.facade.dto;

/**
 * 角色查询参数
 * @author huang.jb
 */
public class RoleQueryParam {
    private String roleName;
    private String roleRemark;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleRemark() {
        return roleRemark;
    }

    public void setRoleRemark(String roleRemark) {
        this.roleRemark = roleRemark;
    }
}
