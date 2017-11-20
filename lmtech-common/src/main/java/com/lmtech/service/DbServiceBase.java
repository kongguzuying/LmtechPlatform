package com.lmtech.service;

import com.lmtech.common.PlatformService;
import com.lmtech.dao.Dao;
import com.lmtech.model.EntityBase;

/**
 * manager base
 * @author huang.jb
 *
 * @param <T>
 */
public interface DbServiceBase<T extends EntityBase> extends PlatformService {
	/**
	 * 获取Dao
	 * @return
     */
	Dao<T> getDao();
}
