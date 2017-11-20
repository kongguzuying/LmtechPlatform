package com.lmtech.facade.request;

import java.util.Map;

/**
 * 服务通用分页带权限认证控制的请求
 * Created by huang.jb on 2017-1-12.
 */
public abstract class PageAuthRequest<T> extends AuthRequest<T> {
    private T pageParam;        //请求参数
    private int pageIndex;      //请求页码
    private int pageSize;       //请求页面大小
    private Map<Object, Object> mapParam;//map请求参数

    public T getPageParam() {
        return pageParam;
    }

    public void setPageParam(T pageParam) {
        this.pageParam = pageParam;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Map<Object, Object> getMapParam() {
        return mapParam;
    }

    public void setMapParam(Map<Object, Object> mapParam) {
        this.mapParam = mapParam;
    }
}
