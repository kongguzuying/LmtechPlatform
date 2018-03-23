package com.lmtech.service;

/**
 * Spring加载完成监听器
 * @author huang.jb
 *
 */
public interface SpringLoadedListener {
	/**
	 * 处理Spring加载完成后事件
	 */
	void handle();
}
