package com.lmtech.infrastructure.facade.request;

import com.lmtech.facade.exceptions.RequestValidateException;
import com.lmtech.facade.request.CommonRequest;
import com.lmtech.infrastructure.model.Group;

/**
 * 群组数据请求
 * Created by huang.jb on 2017-3-29.
 */
public class GroupRequest extends CommonRequest<Group> {
    public void validate() {
        if (super.getReqData() == null) {
            throw new RequestValidateException("传入群组不允许为空");
        }
    }
}
