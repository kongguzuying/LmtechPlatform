package com.lmtech.service.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lmtech.dao.Dao;
import com.lmtech.exceptions.DaoException;
import com.lmtech.model.EntityBase;
import com.lmtech.util.ClassUtil;
import com.lmtech.util.CollectionUtil;
import com.lmtech.util.LoggerManager;
import com.lmtech.util.StringUtil;

/**
 * 抽象数据库多对多关联管理基础类
 * @author huang.jb
 *
 */
public abstract class AbstractDbMTMRelationManagerBaseImpl<T extends EntityBase> extends AbstractDbManagerBaseImpl<T> {

	private static final long serialVersionUID = 1L;
	
	public void setOneToManyRelation(String oneId, List<String> manyIds, String oneFieldName, String manyFieldName, Class<?> clazz) {
		this.checkParameter(oneId, oneFieldName, manyFieldName, clazz);
		this.checkEntity(clazz);

		Dao<T> dao = getDaoByEntity(clazz);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(oneFieldName, oneId);
		try {
			dao.deleteByFieldMap(param, clazz);
		} catch (Exception e) {
			LoggerManager.error(e);
			throw new DaoException(e);
		}
		
		if (manyIds != null && manyIds.size() > 0) {
			for (String manyId : manyIds) {
				Object entity = this.createInstance(clazz);
				ClassUtil.setFieldValue(entity, oneFieldName, oneId);
				ClassUtil.setFieldValue(entity, manyFieldName, manyId);

				preHandleAddObjectEntity(entity);
				dao.insert((T) entity);
			}
		}
	}
	
	public void addOneToManyRelation(String oneId, List<String> manyIds, String oneFieldName, String manyFieldName, Class<?> clazz) {
		this.checkParameter(oneId, oneFieldName, manyFieldName, clazz);
		this.checkEntity(clazz);

		Dao<T> dao = getDaoByEntity(clazz);
		if (manyIds != null && manyIds.size() > 0) {
			for (String manyId : manyIds) {
				Map<String, Object> fieldValues = new HashMap<String, Object>();
				fieldValues.put(oneFieldName, oneId);
				fieldValues.put(manyFieldName, manyId);
				List<?> values = dao.selectByFieldMap(fieldValues, clazz);
				boolean exist = !CollectionUtil.isNullOrEmpty(values);

				if (!exist) {
					Object entity = this.createInstance(clazz);
					ClassUtil.setFieldValue(entity, oneFieldName, oneId);
					ClassUtil.setFieldValue(entity, manyFieldName, manyId);

					preHandleAddObjectEntity(entity);
					dao.insert((T) entity);
				}
			}
		}
	}
	
	public void removeOneToManyRelation(String oneId, List<String> manyIds, String oneFieldName, String manyFieldName, Class<?> clazz) {
		this.checkParameter(oneId, oneFieldName, manyFieldName, clazz);
		
		if (manyIds != null && manyIds.size() > 0) {
			for (String manyId : manyIds) {
				Map<String, Object> fieldValues = new HashMap<String, Object>();
				fieldValues.put(oneFieldName, oneId);
				fieldValues.put(manyFieldName, manyId);
				try {
					getDaoByEntity(clazz).deleteByFieldMap(fieldValues, clazz);
				} catch (Exception e) {
					LoggerManager.error(e);
					throw new DaoException(e);
				}
			}
		}
	}

	private void checkParameter(String oneId, String oneFieldName, String manyFieldName, Class<?> clazz) {
		//检查参数
		if (StringUtil.isNullOrEmpty(oneId)) {
			throw new IllegalArgumentException("主关联ID不允许为空。");
		}
		if (StringUtil.isNullOrEmpty(oneFieldName)) {
			throw new IllegalArgumentException("oneFieldName不允许为空。");
		}
		if (StringUtil.isNullOrEmpty(manyFieldName)) {
			throw new IllegalArgumentException("manyFieldName不允许为空。");
		}
		if (clazz == null) {
			throw new IllegalArgumentException("实体类不允许为空。");
		}
		if (!EntityBase.class.isAssignableFrom(clazz)) {
			throw new IllegalArgumentException("设置关联关系失败，实体类不是从EntityBase继承。");
		}
	}
	
	private void checkEntity(Class<?> clazz) {
		//检查实体
		Object testEntity = this.createInstance(clazz);
		if (testEntity == null) {
			throw new RuntimeException(String.format("设置关联关系失败，类%1$s无法使用默认构造函数实例化。", clazz.getSimpleName()));
		}
	}
	
	private Object createInstance(Class<?> clazz) {
		Object result = null;
		try {
			result = clazz.newInstance();
			return result;
		} catch (InstantiationException e) {
			return null;
		} catch (IllegalAccessException e) {
			return null;
		}
	}
}
