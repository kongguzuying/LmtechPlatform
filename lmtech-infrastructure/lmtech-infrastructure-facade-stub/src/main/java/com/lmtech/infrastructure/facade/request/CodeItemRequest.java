package com.lmtech.infrastructure.facade.request;

import com.lmtech.facade.exceptions.RequestValidateException;
import com.lmtech.facade.request.CommonRequest;
import com.lmtech.infrastructure.model.CodeItem;

/**
 * 代码项数据请求
 * Created by huang.jb on 2017-3-14.
 */
public class CodeItemRequest extends CommonRequest<CodeItem> {
    public void validate() {
        if (super.getReqData() == null) {
            throw new RequestValidateException("传入代码项数据不允许为空");
        }
    }
}
