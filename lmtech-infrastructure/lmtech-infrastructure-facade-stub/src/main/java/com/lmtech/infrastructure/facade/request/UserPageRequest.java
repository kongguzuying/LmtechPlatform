package com.lmtech.infrastructure.facade.request;

import com.lmtech.facade.request.PageRequest;
import com.lmtech.infrastructure.model.User;

/**
 * 用户分页数据请求
 * Created by huang.jb on 2017-4-12.
 */
public class UserPageRequest extends PageRequest<User, Object> {
    public void validate() {

    }
}
