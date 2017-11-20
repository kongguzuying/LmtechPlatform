package com.lmtech.facade.request;

/**
 * 服务通用请求
 * Created by huang.jb on 2017-1-12.
 */
public class NormalFreeRequest extends CommonRequest<Object> {
    @Override
    public void validate() {
        //无需校验
    }
}
