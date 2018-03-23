package com.lmtech.service.support;

import com.lmtech.model.EntityBase;
import com.lmtech.service.DbServiceBase;

/**
 * 抽象数据库服务基础类，用于为系统中的所有数据库服务提供继承
 * @author huang.jb
 *
 * @param <T>
 */
public abstract class AbstractDbServiceBaseImpl<T extends EntityBase> extends AbstractDbBase implements DbServiceBase<T> {
	
	private static final long serialVersionUID = 1L;
}
