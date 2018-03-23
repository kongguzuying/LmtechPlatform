package com.lmtech.infrastructure.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.DbEntityBase;

/**
 * 角色页面资源关联
 * Created by huang.jb on 2016-7-15.
 */
@TableName("LM_RESOURCE_PERMISSION")
public class RoleResourcePermission extends DbEntityBase {
    @TableField("ROLE_ID")
    private String roleId;
    @TableField("RESOURCE_ID")
    private String resourceId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
}
