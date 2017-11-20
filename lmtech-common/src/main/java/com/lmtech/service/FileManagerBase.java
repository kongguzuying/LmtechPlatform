package com.lmtech.service;

import java.io.Serializable;
import java.util.List;

import com.lmtech.common.PlatformService;
import com.lmtech.model.EntityBase;

/**
 * manager base
 * @author huang.jb
 *
 * @param <T>
 */
public interface FileManagerBase<T extends EntityBase> extends PlatformService {
	String add(T t);
	void update(T t);
	void remove(Serializable id);
	T get(Serializable id);
	List<T> getAll();
}
