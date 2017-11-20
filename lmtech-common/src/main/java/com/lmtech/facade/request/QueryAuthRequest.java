package com.lmtech.facade.request;

/**
 * 查询请求
 * Created by huang.jb on 2017-3-27.
 */
public abstract class QueryAuthRequest<T> extends AuthRequest<T> {
    private T queryParam;      //请求参数

    public T getQueryParam() {
        return queryParam;
    }

    public void setQueryParam(T queryParam) {
        this.queryParam = queryParam;
    }
}
