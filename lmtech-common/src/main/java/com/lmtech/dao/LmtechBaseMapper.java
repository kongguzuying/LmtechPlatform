package com.lmtech.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * 基础BaseMapper
 * @param <T>
 *
 * @author huangjb
 */
public interface LmtechBaseMapper<T> extends BaseMapper<T> {
    /**
     * 查询列表数据
     * @param param
     * @return
     */
    List<T> selectDataList(T param);

    /**
     * 查询页面列表数据
     * @param param
     * @return
     */
    List<T> selectPageList(T param);
}
