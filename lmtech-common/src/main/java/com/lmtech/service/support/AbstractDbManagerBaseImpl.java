package com.lmtech.service.support;

import com.lmtech.common.Context;
import com.lmtech.common.ContextManager;
import com.lmtech.common.PageData;
import com.lmtech.exceptions.DataVersionException;
import com.lmtech.model.ConfigEntityBase;
import com.lmtech.model.DbEntityBase;
import com.lmtech.model.EntityBase;
import com.lmtech.service.*;
import com.lmtech.util.*;
import org.mybatis.spring.SqlSessionTemplate;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 抽象数据库管理基础类，用于为系统所有数据库管理类提供继承
 * @author huang.jb
 *
 * @param <T>
 */
public abstract class AbstractDbManagerBaseImpl<T extends EntityBase> extends AbstractDbBase implements DbManagerBase<T> {
	
	private static final long serialVersionUID = 1L;
	
	private List<ManagerAddBeforeListener> addBeforeListeners;
	private List<ManagerAddAfterListener> addAfterListeners;
	private List<ManagerUpdateBeforeListener> updateBeforeListeners;
	private List<ManagerUpdateAfterListener> updateAfterListeners;
	private List<ManagerRemoveBeforeListener> removeBeforeListeners;
	private List<ManagerRemoveAfterListener> removeAfterListeners;
	
	@Override
	public String add(T t) {
		return addObjectEntity(t);
	}

	@Override
	public void edit(T t) {
		if (t != null) {
			if (updateBeforeListeners != null && updateBeforeListeners.size() > 0) {
				for (ManagerUpdateBeforeListener listener : updateBeforeListeners) {
					listener.actionPerform(t);
				}
			}

			preHandleEditObjectEntity(t);

			//更新数据
			getDao().updateById(t);
			
			if (updateAfterListeners != null && updateAfterListeners.size() > 0) {
				for (ManagerUpdateAfterListener listener : updateAfterListeners) {
					try {
						listener.actionPerform(t);
					} catch (Exception e) {
						LoggerManager.error(e);
					}
				}
			}
		} else {
			throw new IllegalArgumentException("更新的模型不允许为空。");
		}
	}

	/**
	 * 数据更新前处理
	 * @param t
     */
	protected void preHandleEditObjectEntity(Object t) {
		EntityBase entity = null;
		if (t instanceof EntityBase) {
			entity = (EntityBase) t;
            entity.setUpdateDate(new Date());
			Context context = ContextManager.getContext();
			if (StringUtil.isNullOrEmpty(entity.getUpdateUser())) {
				entity.setUpdateUser(context != null ? context.getUserId() : null);
			}
			if (StringUtil.isNullOrEmpty(entity.getUpdateUserName())) {
				entity.setUpdateUserName(context != null ? context.getUserName() : null);
			}
        }

		String dataId = null;
		if (t instanceof DbEntityBase) {
			DbEntityBase dbEntity = (DbEntityBase) t;
			dataId = dbEntity.getId();
		} else if (t instanceof ConfigEntityBase) {
			ConfigEntityBase configEntity = (ConfigEntityBase) t;
			dataId = configEntity.getCode();
		}

		if (entity != null && !StringUtil.isNullOrEmpty(dataId)) {
			//数据版本校验
			Object object = getDao().selectById(dataId);
			if (object != null) {
				EntityBase dbEntity = (EntityBase) object;
				if (entity.getDataVersion() < dbEntity.getDataVersion()) {
					throw new DataVersionException();
				} else {
					entity.setDataVersion(dbEntity.getDataVersion() + 1);
				}
			}
		}
	}

	@Override
	public void remove(Serializable id) {
		if (id == null || StringUtil.isNullOrEmpty(id.toString())) {
			throw new IllegalArgumentException("删除模型失败，ID不允许为空。");
		} else {
			if (addBeforeListeners != null && addBeforeListeners.size() > 0) {
				for (ManagerAddBeforeListener listener : addBeforeListeners) {
					listener.actionPerform(id);
				}
			}

			//删除数据
			getDao().deleteById(id);
			
			if (addAfterListeners != null && addAfterListeners.size() > 0) {
				for (ManagerAddAfterListener listener : addAfterListeners) {
					try {
						listener.actionPerform(id);
					} catch (Exception e) {
						LoggerManager.error(e);
					}
				}
			}
		}
	}

	@Override
	public T get(Serializable id) {
		if (id != null && !StringUtil.isNullOrEmpty(id.toString())) {
			Object data = getDao().selectById(id);
			if (data != null) {
				if (data instanceof EntityBase) {
					if (!((EntityBase) data).isDelete()) {
						return (T) data;
					} else {
						return null;
					}
				} else {
					return (T) data;
				}
			} else {
				return null;
			}
		} else {
			throw new RuntimeException("get data failed,id could not be null.");
		}
	}

	@Override
	public List<T> getAll() {
		return getDao().selectList(null);
	}

	@Override
	public List<T> getList(T param) {
		return getDao().getDataList(param);
	}

	@Override
	public PageData<T> getDataListOfPage(T param, int pageIndex, int pageSize) {
		this.pageValidate(pageIndex, pageSize);
		PageHelper.startPage(pageIndex, pageSize);
		getDao().getDataList(param);
		PageData pageData = PageHelper.endPage();
		return pageData;
	}

	@Override
	public PageData getPageData(int pageIndex, int pageSize) {
		this.pageValidate(pageIndex, pageSize);
		PageHelper.startPage(pageIndex, pageSize);
		getAll();
		PageData pageData = PageHelper.endPage();
		return pageData;
	}

	@Override
	public PageData getPageData(T param, int pageIndex, int pageSize) {
		this.pageValidate(pageIndex, pageSize);
		PageHelper.startPage(pageIndex, pageSize);
		getDao().getPageList(param);
		PageData pageData = PageHelper.endPage();
		return pageData;
	}
	
	@Override
	public PageData getPageData(Map<Object, Object> params, int pageIndex, int pageSize) {
		if (params != null && params.size() > 0) {
			PageHelper.startPage(pageIndex, pageSize);
			getDao().selectByMap(params);
			return PageHelper.endPage();
		} else {
			return getPageData(pageIndex, pageSize);
		}
	}
	
	@Override
	public PageData getPageData(Map<Object, Object> params, Map<String, String> orderBys, int pageIndex, int pageSize) {
		return getPageData(params, null, pageIndex, pageSize);
	}
	
	@Override
	@Deprecated
	public PageData getPageData(StringBuilder hql, int pageIndex, int pageSize) {
		return null;
	}
	
	protected void pageValidate(int pageIndex, int pageSize) {
		if (pageIndex <= 0) {
			throw new IllegalArgumentException(String.format("无效的PageIndex参数:%1$d", pageIndex));
		}
		if (pageSize <= 0) {
			throw new IllegalArgumentException(String.format("无效的PageSize参数:%1$d", pageIndex));
		}
	}

	/**
	 * 添加Object类型实体
	 * @param t
	 * @return
     */
	protected String addObjectEntity(Object t) {
		if (t != null) {
			if (addBeforeListeners != null && addBeforeListeners.size() > 0) {
				for (ManagerAddBeforeListener listener : addBeforeListeners) {
					listener.actionPerform(t);
				}
			}

			String id = preHandleAddObjectEntity(t);

			//插入数据
			getDao().insert(t);

			if (addAfterListeners != null && addAfterListeners.size() > 0) {
				for (ManagerAddAfterListener listener : addAfterListeners) {
					try {
						listener.actionPerform(t);
					} catch (Exception e) {
						LoggerManager.error(e);
					}
				}
			}

			return id;
		} else {
			throw new IllegalArgumentException("添加的模型不允许为空。");
		}
	}

	/**
	 * 数据插入前处理
	 * @param t
     * @return
     */
	protected String preHandleAddObjectEntity(Object t) {
		Context context = ContextManager.getContext();
		String id = "";
		if (t instanceof DbEntityBase) {
            DbEntityBase entity = (DbEntityBase) t;
            if (entity.getId() == null || entity.getId().equals("")) {
                id = IdWorkerUtil.generateStringId();
                entity.setId(id);
            }
        }
		if (t instanceof EntityBase) {
            EntityBase entity = (EntityBase) t;
            entity.setCreateDate(new Date());
			entity.setUpdateDate(new Date());
			entity.setDataVersion(1);
            if (StringUtil.isNullOrEmpty(entity.getCreateUser())) {
				entity.setCreateUser(context != null ? context.getUserId() : null);
			}
			if (StringUtil.isNullOrEmpty(entity.getCreateUserName())) {
				entity.setCreateUserName(context != null ? context.getUserName() : null);
			}
			if (StringUtil.isNullOrEmpty(entity.getUpdateUser())) {
				entity.setUpdateUser(context != null ? context.getUserId() : null);
			}
			if (StringUtil.isNullOrEmpty(entity.getUpdateUserName())) {
				entity.setUpdateUserName(context != null ? context.getUserName() : null);
			}
        }
		return id;
	}

	// property
	public List<ManagerAddBeforeListener> getAddBeforeListeners() {
		return addBeforeListeners;
	}
	public void setAddBeforeListeners(List<ManagerAddBeforeListener> addBeforeListeners) {
		this.addBeforeListeners = addBeforeListeners;
	}
	public List<ManagerAddAfterListener> getAddAfterListeners() {
		return addAfterListeners;
	}
	public void setAddAfterListeners(List<ManagerAddAfterListener> addAfterListeners) {
		this.addAfterListeners = addAfterListeners;
	}
	public List<ManagerUpdateBeforeListener> getUpdateBeforeListeners() {
		return updateBeforeListeners;
	}
	public void setUpdateBeforeListeners(List<ManagerUpdateBeforeListener> updateBeforeListeners) {
		this.updateBeforeListeners = updateBeforeListeners;
	}
	public List<ManagerUpdateAfterListener> getUpdateAfterListeners() {
		return updateAfterListeners;
	}
	public void setUpdateAfterListeners(List<ManagerUpdateAfterListener> updateAfterListeners) {
		this.updateAfterListeners = updateAfterListeners;
	}
	public List<ManagerRemoveBeforeListener> getRemoveBeforeListeners() {
		return removeBeforeListeners;
	}
	public void setRemoveBeforeListeners(List<ManagerRemoveBeforeListener> removeBeforeListeners) {
		this.removeBeforeListeners = removeBeforeListeners;
	}
	public List<ManagerRemoveAfterListener> getRemoveAfterListeners() {
		return removeAfterListeners;
	}
	public void setRemoveAfterListeners(List<ManagerRemoveAfterListener> removeAfterListeners) {
		this.removeAfterListeners = removeAfterListeners;
	}
}
