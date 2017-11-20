package com.lmtech.service;

/**
 * 管理器添加后事件
 * @author huang.jb
 *
 */
public interface ManagerAddAfterListener {
	/**
	 * 动作执行
	 * @param entity 添加的实体
	 */
	void actionPerform(Object entity);
}
