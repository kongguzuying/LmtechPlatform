package com.lmtech.util;

import com.lmtech.common.ExeResult;

import java.util.List;

/**
 * 集合批次处理接口
 * Created by huang.jb on 2017-1-12.
 */
public interface CollectionBatchHandler<T> {
    /**
     * 集合批次处理
     * @param list
     * @return
     */
    ExeResult handle(List<T> list);
}
