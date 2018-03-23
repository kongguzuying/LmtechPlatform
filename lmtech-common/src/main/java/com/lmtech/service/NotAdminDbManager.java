package com.lmtech.service;

import java.util.List;
import java.util.Map;

import com.lmtech.common.PageData;

/**
 * 非管理员资源管理接口
 * @author huang.jb
 *
 */
public interface NotAdminDbManager {
	/**
	 * 获取所有实体数据（非管理员资源）
	 * @return
	 */
	List<?> getAllNotAdmin();
	/**
	 * 获取所有实体数据，通过指定条件（非管理员资源）
	 * 参数格式：:id,:name,支持in(:ids)
	 * @param params
	 * @return
	 */
	List<?> getAllNotAdmin(Map<String, Object> params);
	/**
	 * 获取实体分页数据（非管理员资源）
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageData getPageDataNotAdmin(int pageIndex, int pageSize);
	/**
	 * 获取实体分页数据，通过指定条件（非管理员资源）
	 * 参数格式：:id,:name,支持in(:ids)
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageData getPageDataNotAdmin(Map<String, Object> params, int pageIndex, int pageSize);
}
