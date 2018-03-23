package com.lmtech.service.support;

import com.lmtech.dao.Dao;
import com.lmtech.model.EntityBase;
import com.lmtech.util.CollectionUtil;
import com.lmtech.util.SpringUtil;
import com.lmtech.util.StringUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

public abstract class AbstractDbBase<T extends EntityBase> {
	/**
	 * 获取Dao，供子类实现
	 *
	 * @return
	 */
	public abstract Dao<T> getDao();

	/**
	 * 根据实体获取Dao
	 *
	 * @param entityClass
	 * @return
	 */
	public Dao<T> getDaoByEntity(Class<?> entityClass) {
		String lowerEntityName = StringUtil.setFirstLower(entityClass.getSimpleName());
		String beanId = lowerEntityName + "DaoImpl";
		Object daoBean = SpringUtil.getObject(beanId);
		return (Dao<T>) daoBean;
	}

	protected Class<?> getEntityClass() {
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		Type[] types = type.getActualTypeArguments();

		if (types != null && types.length > 0) {
			return (Class<?>) types[0];
		} else {
			return null;
		}
	}

	/**
	 * 构建查询条件，key,value,key,value...
	 *
	 * @param params
	 * @return
	 */
	protected Map<String, Object> buildParams(Object... params) {
		return CollectionUtil.buildStringObjectMap(params);
	}
}

