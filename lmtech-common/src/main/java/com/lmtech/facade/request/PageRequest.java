package com.lmtech.facade.request;

/**
 * 服务通用分页请求
 * Created by huang.jb on 2017-1-12.
 */
public abstract class PageRequest<P, T> extends CommonRequest<T> {
    private P pageParam;        //请求参数
    private int pageIndex;      //请求页码
    private int pageSize;       //请求页面大小

    public P getPageParam() {
        return pageParam;
    }

    public void setPageParam(P pageParam) {
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
}
