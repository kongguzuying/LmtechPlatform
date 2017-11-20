package com.lmtech.exceptions;

/**
 * 消息类异常，不作日志记录，只返回错误消息
 * @author huang.jb
 *
 */
public interface MessageException {
	/**
	 * 获取异常消息
	 * @return
	 */
	String getMessage();
}
