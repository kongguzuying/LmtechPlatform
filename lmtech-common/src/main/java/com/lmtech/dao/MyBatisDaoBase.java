package com.lmtech.dao;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lmtech.exceptions.DaoException;
import com.lmtech.model.EntityBase;
import com.lmtech.util.ClassUtil;
import com.lmtech.util.StringUtil;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MyBatis Dao基础类，整合MybatisPlus框架
 * Created by huang.jb on 2017-2-28.
 */
public class MyBatisDaoBase<M extends LmtechBaseMapper<T>, T extends EntityBase> extends ServiceImpl<M, T> implements Dao<T> {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<T> getDataList(T param) {
        //Mapper未实现selectDataList时，返回所有数据
        if(existMapperId("selectDataList")) {
            return baseMapper.selectDataList(param);
        } else {
            return baseMapper.selectList(null);
        }
    }

    @Override
    public List<T> getPageList(T param) {
        //Mapper未实现selectPageList时，返回所有数据
        if(existMapperId("selectPageList")) {
            return baseMapper.selectPageList(param);
        } else {
            return baseMapper.selectList(null);
        }
    }

    @Override
    public void deleteByFieldMap(Map<String, Object> fieldValues) {
        try {
            Class<?> entityClass = getEntityClass();
            Map<String, Object> columnMaps = getColumnMapByFieldMap(fieldValues, entityClass);
            deleteByMap(columnMaps);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteByFieldMap(Map<String, Object> fieldValues, Class<?> entityClass) {
        try {
            Map<String, Object> columnMaps = getColumnMapByFieldMap(fieldValues, entityClass);
            deleteByMap(columnMaps);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<T> selectByFieldMap(Map<String, Object> fieldValues) {
        try {
            Class<?> entityClass = getEntityClass();
            Map<String, Object> columnMaps = getColumnMapByFieldMap(fieldValues, entityClass);
            List<T> ts = selectByMap(columnMaps);
            return ts;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<T> selectByFieldMap(Map<String, Object> fieldValues, Class<?> entityClass) {
        try {
            Map<String, Object> columnMaps = getColumnMapByFieldMap(fieldValues, entityClass);
            List<T> ts = selectByMap(columnMaps);
            return ts;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    private Map<String, Object> getColumnMapByFieldMap(Map<String, Object> fieldValues, Class<?> entityClass) throws Exception {
        //构建列值Map
        Map<String, Object> columnMaps = new HashMap<String, Object>();
        if (fieldValues != null && fieldValues.size() > 0) {
            for (String fieldName : fieldValues.keySet()) {
                Field field = ClassUtil.getFieldOfHierarchy(fieldName, entityClass);
                TableField tableField = field.getAnnotation(TableField.class);

                if (tableField != null && tableField.exist()) {
                    if (!StringUtil.isNullOrEmpty(tableField.value())) {
                        columnMaps.put(tableField.value(), fieldValues.get(fieldName));
                    } else {
                        columnMaps.put(fieldName, fieldValues.get(fieldName));
                    }
                }
            }
        }

        return columnMaps;
    }

    /**
     * 获取实体类
     * @return
     */
    protected Class<?> getEntityClass() {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (params != null && params.length > 1) {
            Class<?> entityClass = (Class) params[1];
            return entityClass;
        } else {
            return null;
        }
    }

    private boolean existMapperId(String mapperId) {
        try {
            Class mapperClass = ClassUtil.getGenericClass(getClass(), 0);
            String id = mapperClass.getName() + "." + mapperId;
            sqlSessionTemplate.getConfiguration().getMappedStatement(id);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }
}
