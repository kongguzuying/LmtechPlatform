package com.lmtech.service;

/**
 * 管理器更新后事件
 * @author huang.jb
 *
 */
public interface ManagerUpdateAfterListener {
	/**
	 * 动作执行
	 * @param entity 添加的实体
	 */
	void actionPerform(Object entity);
}
