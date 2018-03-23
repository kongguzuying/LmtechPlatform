package com.lmtech.infrastructure.facade.request;

import com.lmtech.facade.exceptions.RequestValidateException;
import com.lmtech.facade.request.CommonRequest;
import com.lmtech.util.CollectionUtil;
import com.lmtech.util.StringUtil;

import java.util.List;

/**
 * 用户和角色Id列表请求
 * Created by huang.jb on 2017-4-1.
 */
public class UserAndRoleIdsRequest extends CommonRequest {
    private String userId;
    private List<String> roleIds;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }

    public void validate() {
        if (StringUtil.isNullOrEmpty(userId)) {
            throw new RequestValidateException("传入用户id不允许为空");
        }
        if (CollectionUtil.isNullOrEmpty(roleIds)) {
            throw new RequestValidateException("传入角色Id列表不允许为空");
        }
    }
}
