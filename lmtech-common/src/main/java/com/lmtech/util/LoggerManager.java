package com.lmtech.util;

import com.lmtech.common.ContextManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 日志工具类
 * @author huang.jb
 *
 */
public class LoggerManager {
	public static final String LEVEL_DEBUG = "DEBUG";
	public static final String LEVEL_INFO = "INFO";
	public static final String LEVEL_WARN = "WARN";
	public static final String LEVEL_ERROR = "ERROR";
	
	public static final String APPENDER_CONSOLE = "CONSOLE";
	public static final String APPENDER_FILE = "ROLLING_FILE";

	private static final Log logger = LogFactory.getLog("lmtech-logger");

	/**
	 * 输出信息
	 * @param message
	 */
	public static void info(String message) {
		message = getSerialNumberPrefix() + message;
		logger.info(message);
	}
	/**
	 * 输出调试信息
	 * @param message
	 */
	public static void debug(String message) {
		message = getSerialNumberPrefix() + message;
		logger.debug(message);
	}
	/**
	 * 输出警告信息
	 * @param message
	 */
	public static void warn(String message) {
		message = getSerialNumberPrefix() + message;
		logger.warn(message);
	}
	/**
	 * 输出错误信息
	 * @param message
	 */
	public static void error(String message) {
		message = getSerialNumberPrefix() + message;
		logger.error(message);
	}
	/**
	 * 输出错误信息
	 * @param e
	 */
	public static void error(Exception e) {
		String message = getSerialNumberPrefix() + (e != null ? e.getMessage() : "null");
		logger.error(message, e);
	}
	/**
	 * 输出错误信息
	 * @param message
	 * @param e
	 */
	public static void error(String message, Exception e) {
		message = getSerialNumberPrefix() + message;
		logger.error(message, e);
	}
	/**
	 * 输出致命错误信息
	 * @param message
	 */
	public static void fatal(String message) {
		message = getSerialNumberPrefix() + message;
		logger.fatal(message);
	}
	/**
	 * 输出致命错误信息
	 * @param message
	 * @param e
	 */
	public static void fatal(String message, Exception e) {
		message = getSerialNumberPrefix() + message;
		logger.fatal(message, e);
	}

	private static  String getSerialNumberPrefix() {
		String serialNumber = ContextManager.getSerialNumber();
		if (!StringUtil.isNullOrEmpty(serialNumber)) {
			return "[SN:" + serialNumber + "] ";
		} else {
			return "";
		}
	}
}
