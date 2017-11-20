package com.lmtech.infrastructure.facade.request;

import com.lmtech.facade.exceptions.RequestValidateException;
import com.lmtech.facade.request.CommonRequest;
import com.lmtech.infrastructure.model.User;

/**
 * 用户数据请求
 * Created by huang.jb on 2017-4-1.
 */
public class UserRequest extends CommonRequest<User> {

    public void validate() {
        if (super.getReqData() == null) {
            throw new RequestValidateException("传入用户数据不允许为空");
        }
    }
}
