package com.lmtech.infrastructure.facade.request;

import com.lmtech.facade.exceptions.RequestValidateException;
import com.lmtech.facade.request.CommonRequest;
import com.lmtech.infrastructure.model.Role;

/**
 * 角色数据请求
 * Created by huang.jb on 2017-3-29.
 */
public class RoleRequest extends CommonRequest<Role> {
    public void validate() {
        if (super.getReqData() == null) {
            throw new RequestValidateException("传入角色数据不允许为空");
        }
    }
}
