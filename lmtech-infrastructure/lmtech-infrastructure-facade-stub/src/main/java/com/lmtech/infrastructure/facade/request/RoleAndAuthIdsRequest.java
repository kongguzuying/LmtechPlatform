package com.lmtech.infrastructure.facade.request;

import com.lmtech.facade.request.CommonRequest;
import com.lmtech.util.CollectionUtil;
import com.lmtech.util.StringUtil;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 角色菜单Id数据请求
 * Created by huang.jb on 2017-3-30.
 */
public class RoleAndAuthIdsRequest extends CommonRequest {

    private String roleId;
    private List<String> authIds;

    public RoleAndAuthIdsRequest() {

    }

    public RoleAndAuthIdsRequest(String roleId, List<String> authIds) {
        this.roleId = roleId;
        this.authIds = authIds;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public List<String> getAuthIds() {
        return authIds;
    }

    public void setAuthIds(List<String> authIds) {
        this.authIds = authIds;
    }

    public void validate() {
        if (StringUtil.isNullOrEmpty(roleId)) {
            Assert.notNull(roleId, "传入角色id不允许为空");
        }
        if (CollectionUtil.isNullOrEmpty(authIds)) {
            Assert.notEmpty(authIds, "传入授权id列表不允许为空");
        }
    }
}
