package com.lmtech.facade.request;

/**
 * 服务通用请求
 * Created by Administrator on 2017-1-12.
 */
public class NormalRequest extends CommonRequest<Object> {
    @Override
    public void validate() {
        //无需校验
    }
}
