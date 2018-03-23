package com.lmtech.infrastructure.service;

import java.util.Map;

import com.lmtech.infrastructure.exceptions.SystemConfigNotExistException;
import com.lmtech.infrastructure.model.SystemConfig;
import com.lmtech.service.DbServiceBase;

/**
 * 系统参数服务
 * @author huang.jb
 *
 */
public interface SystemConfigService extends DbServiceBase<SystemConfig> {
	/**
	 * 获取系统参数
	 * @param code
	 * @return
     */
	SystemConfig getSystemConfig(String code);
	/**
	 * 获取系统参数字符串值
	 * @param code
	 * @return
	 * @throws SystemConfigNotExistException
	 */
	String getStringValue(String code) throws SystemConfigNotExistException;
	/**
	 * 获取系统参数整形值
	 * @param code
	 * @return
	 * @throws SystemConfigNotExistException
	 */
	int getIntValue(String code) throws SystemConfigNotExistException;
	/**
	 * 获取系统参数长整形值
	 * @param code
	 * @return
	 * @throws SystemConfigNotExistException
	 */
	long getLongValue(String code) throws SystemConfigNotExistException;
	/**
	 * 获取系统参数double型值
	 * @param code
	 * @return
	 * @throws SystemConfigNotExistException
	 */
	double getDoubleValue(String code) throws SystemConfigNotExistException;
	/**
	 * 获取系统参数布尔值
	 * @param code
	 * @return
	 * @throws SystemConfigNotExistException
	 */
	boolean getBooleanValue(String code) throws SystemConfigNotExistException;
	/**
	 * 获取所有系统参数的Map形式，KEY为参数名，VALUE为参数值
	 * @return
	 */
	Map<String, String> getAllOfMap();
}
