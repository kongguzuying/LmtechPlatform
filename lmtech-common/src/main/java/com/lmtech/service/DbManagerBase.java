package com.lmtech.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.lmtech.common.PageData;
import com.lmtech.common.PlatformService;
import com.lmtech.model.EntityBase;

/**
 * manager base
 * @author huang.jb
 *
 * @param <T>
 */
public interface DbManagerBase<T extends EntityBase> extends PlatformService {
	/**
	 * 添加实体
	 * @param t
	 * @return
	 */
	String add(T t);
	/**
	 * 更新实体
	 * @param t
	 */
	void edit(T t);
	/**
	 * 删除实体
	 * @param id
	 */
	void remove(Serializable id);
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	T get(Serializable id);
	/**
	 * 获取所有实体数据
	 * @return
	 */
	List<T> getAll();
	/**
	 * 获取列表数据
	 * @param param
	 * @return
	 */
	List<T> getList(T param);
	/**
	 * 获取实体分页数据（应用于接口）
	 * @param param
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageData<T> getDataListOfPage(T param, int pageIndex, int pageSize);
	/**
	 * 获取实体分页数据（应用于管理系统）
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageData getPageData(int pageIndex, int pageSize);
	/**
	 * 获取实体分页数据（应用于管理系统）
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageData getPageData(T param, int pageIndex, int pageSize);
	/**
	 * 获取实体分页数据，通过指定条件（应用于管理系统）
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageData getPageData(Map<Object, Object> params, int pageIndex, int pageSize);
	/**
	 * 获取实体分页数据，通过指定条件及排序条件（应用于管理系统）
	 * @param params
	 * @param orderBys
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageData getPageData(Map<Object, Object> params, Map<String, String> orderBys, int pageIndex, int pageSize);
	/**
	 * 获取实体分页数据，通过HQL
	 * @param hql
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@Deprecated
	PageData getPageData(StringBuilder hql, int pageIndex, int pageSize);
}
