package com.lmtech.facade.request;

import com.lmtech.facade.exceptions.RequestValidateException;
import com.lmtech.util.StringUtil;

/**
 * 字符串请求
 * Created by huang.jb on 2017-2-23.
 */
public class StringFreeRequest extends CommonRequest<String> {
    public StringFreeRequest() {

    }

    public StringFreeRequest(String value) {
        super.setReqData(value);
    }

    @Override
    public void validate() {
        if (StringUtil.isNullOrEmpty(super.getReqData())) {
            throw new RequestValidateException("传入字符串参数不允许为空");
        }
    }
}
