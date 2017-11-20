package com.lmtech.infrastructure.facade.request;

import com.lmtech.facade.exceptions.RequestValidateException;
import com.lmtech.facade.request.PageRequest;
import com.lmtech.infrastructure.model.Role;
import com.lmtech.util.StringUtil;

/**
 * 用户角色分页数据请求
 * Created by huang.jb on 2017-4-1.
 */
public class UserRolePageRequest extends PageRequest<Role, Object> {

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void validate() {
        if (StringUtil.isNullOrEmpty(userId)) {
            throw new RequestValidateException("传入用户id不允许为空");
        }
    }
}
