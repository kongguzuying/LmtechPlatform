package com.lmtech.dao;

import com.baomidou.mybatisplus.service.IService;
import com.lmtech.model.EntityBase;

import java.util.List;
import java.util.Map;

/**
 * Dao接口
 * Created by huang.jb on 2017-2-28.
 */
public interface Dao<T extends EntityBase> extends IService<T> {
    /**
     * 获取列表数据
     * @param param
     * @return
     */
    List<T> getDataList(T param);

    /**
     * 获取列表数据（专门用于管理接口列表查询）
     * @param param
     * @return
     */
    List<T> getPageList(T param);

    /**
     * 通过实体属性删除
     * @param fieldValues
     */
    void deleteByFieldMap(Map<String, Object> fieldValues);

    /**
     * 通过实体属性删除，指定实体类
     * @param fieldValues
     * @param entityClass
     */
    void deleteByFieldMap(Map<String, Object> fieldValues, Class<?> entityClass);

    /**
     * 通过实体属性搜索
     * @param fieldValues
     */
    List<T> selectByFieldMap(Map<String, Object> fieldValues);

    /**
     * 通过实体属性搜索，指定实体类
     * @param fieldValues
     * @param entityClass
     */
    List<T> selectByFieldMap(Map<String, Object> fieldValues, Class<?> entityClass);
}
