package com.lmtech.facade.request;

import com.lmtech.facade.exceptions.RequestValidateException;
import com.lmtech.util.CollectionUtil;

import java.util.List;

/**
 * String列表的请求
 * Created by huang.jb on 2017-1-22.
 */
public class StringListRequest extends CommonRequest<List<String>> {
    @Override
    public void validate() {
        if (CollectionUtil.isNullOrEmpty(super.getReqData())) {
            throw new RequestValidateException("传入字符串列表参数不允许为空");
        }
    }
}
