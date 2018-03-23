package com.lmtech.service;

import java.io.Serializable;

/**
 * 管理器删除后事件
 * @author huang.jb
 *
 */
public interface ManagerRemoveBeforeListener {
	/**
	 * 动作执行
	 * @param id 主键ID
	 */
	void actionPerform(Serializable id);
}
