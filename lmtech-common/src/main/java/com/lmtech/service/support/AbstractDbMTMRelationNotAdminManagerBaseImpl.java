package com.lmtech.service.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lmtech.common.PageData;
import com.lmtech.model.ConfigEntityBase;
import com.lmtech.model.EntityBase;
import com.lmtech.service.NotAdminDbManager;

/**
 * 抽象数据库多对多关联管理基础类，以及包含区分管理员与非管理员资源操作的管理类
 * @author huang.jb
 *
 */
public abstract class AbstractDbMTMRelationNotAdminManagerBaseImpl<T extends EntityBase> extends AbstractDbMTMRelationManagerBaseImpl<T> implements NotAdminDbManager {

	private static final long serialVersionUID = 1L;
	
	@Override
	public List<?> getAllNotAdmin() {
		return this.getAllNotAdmin(null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<?> getAllNotAdmin(Map<String, Object> params) {
		Class<?> clazz = this.getEntityClass();
		String primaryKeyColumn = "id";
		if (ConfigEntityBase.class.isAssignableFrom(clazz)) {
			primaryKeyColumn = "code";
		}
		
		StringBuilder hql = new StringBuilder(String.format("from %1$s where %2$s not in(:ids)", clazz.getSimpleName(), primaryKeyColumn));
		
		if (params != null && params.size() > 0) {
			for (String key : params.keySet()) {
				hql.append("and " + key + "=:" + key);
			}
		}
		hql.append(" order by createDate");

		if (params == null) {
			params = new HashMap<String, Object>();
		}
		List<String> resourceIds = this.getAdminPermission();
		//防止参数为空时报错
		if (resourceIds.size() <= 0) {
			resourceIds.add("");
		}
		params.put("ids", resourceIds);
		return null;// (List<T>) daoBase.find(hql.toString(), params);
	}

	@Override
	public PageData getPageDataNotAdmin(int pageIndex, int pageSize) {
		return this.getPageDataNotAdmin(null, pageIndex, pageSize);
	}

	@Override
	public PageData getPageDataNotAdmin(Map<String, Object> params, int pageIndex, int pageSize) {
		super.pageValidate(pageIndex, pageSize);
		Class<?> clazz = this.getEntityClass();
		
		StringBuilder hql = new StringBuilder(String.format("from %1$s where 1=1 and id not in(:ids) ", clazz.getSimpleName()));
		
		if (params != null && params.size() > 0) {
			for (String key : params.keySet()) {
				hql.append("and " + key + "=:" + key);
			}
		}
		hql.append(" order by createDate");
		
		if (params == null) {
			params = new HashMap<String, Object>();
		}
		List<String> resourceIds = this.getAdminPermission();
		//防止参数为空时报错
		if (resourceIds.size() <= 0) {
			resourceIds.add("");
		}
		params.put("ids", resourceIds);
		return null;//daoBase.query(hql.toString(), pageIndex, pageSize, params);
	}

	/**
	 * 获取管理员资源权限，应用子类实现
	 * @return
	 */
	public abstract List<String> getAdminPermission();
}
