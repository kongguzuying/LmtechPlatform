package com.lmtech.infrastructure.facade.request;

import com.lmtech.facade.exceptions.RequestValidateException;
import com.lmtech.facade.request.CommonRequest;
import com.lmtech.infrastructure.model.CodeType;

/**
 * 代码类型数据请求
 * Created by huang.jb on 2017-3-14.
 */
public class CodeTypeRequest extends CommonRequest<CodeType> {
    public void validate() {
        if (super.getReqData() == null) {
            throw new RequestValidateException("传入代码分类数据不允许为空");
        }
    }
}
