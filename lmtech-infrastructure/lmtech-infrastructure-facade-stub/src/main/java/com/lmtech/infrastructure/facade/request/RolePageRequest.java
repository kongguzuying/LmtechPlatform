package com.lmtech.infrastructure.facade.request;

import com.lmtech.facade.request.PageRequest;
import com.lmtech.infrastructure.facade.dto.RoleQueryParam;
import com.lmtech.infrastructure.model.Role;

/**
 * 角色分页数据请求
 * Created by huang.jb on 2017-3-29.
 */
public class RolePageRequest extends PageRequest<RoleQueryParam> {

    public void validate() {

    }
}
