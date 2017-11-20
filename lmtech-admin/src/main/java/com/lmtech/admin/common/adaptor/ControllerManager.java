package com.lmtech.admin.common.adaptor;

import com.lmtech.common.PageData;

import java.util.List;

/**
 * 控制器管理接口
 * Created by huang.jb on 2017-4-1.
 */
public interface ControllerManager<T> {
    String add(T codeType);

    String edit(T codeType);

    void remove(String id);

    T get(String id);

    List<T> getAll();

    PageData<T> getPageData(T param, int pageIndex, int pageSize);
}
