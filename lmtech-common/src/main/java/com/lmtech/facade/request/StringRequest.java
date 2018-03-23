package com.lmtech.facade.request;

import com.lmtech.facade.exceptions.RequestValidateException;
import com.lmtech.util.StringUtil;

/**
 * String的请求
 * Created by huang.jb on 2017-1-22.
 */
public class StringRequest extends CommonRequest<String> {

    public StringRequest() {

    }

    public StringRequest(String value) {
        super.setReqData(value);
    }

    @Override
    public void validate() {
        if (StringUtil.isNullOrEmpty(super.getReqData())) {
            throw new RequestValidateException("传入字符串参数不允许为空");
        }
    }
}
