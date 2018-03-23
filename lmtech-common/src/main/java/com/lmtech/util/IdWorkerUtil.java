package com.lmtech.util;

import java.util.Date;

import com.lmtech.common.ApplicationConfig;
import com.lmtech.service.IdWorker;

/**
 * 唯一主键生成工具类
 * @author huang.jb
 *
 */
public class IdWorkerUtil {
	
	private static IdWorker idWorker = new IdWorker(ApplicationConfig.SYSTEM_ID, ApplicationConfig.DATACENTER_ID);
	
	/**
	 * 生成关键主键ID
	 * 该主键生成策略采用Twitter的分布式自增ID算法Snowflake实现，可靠性优于UUID，适用于更严格的场景如订单编号生成
	 * @return
	 */
	public static long generateId() {
		return idWorker.nextId();
	}

	/**
	 * 生成关键主键ID
	 * 该主键生成策略采用Twitter的分布式自增ID算法Snowflake实现，可靠性优于UUID，适用于更严格的场景如订单编号生成
	 * @return
     */
	public static String generateStringId() {
		long id = generateId();
		return String.valueOf(id);
	}
	/**
	 * 生成关键主键ID，以日期开头
	 * 该主键生成策略采用Twitter的分布式自增ID算法Snowflake实现，可靠性优于UUID，适用于更严格的场景如订单编号生成
	 * @return
	 */
	public static String generateIdStartWithDate() {
		String dateStr = DateUtil.format(new Date(), "yyyyMMddHHmmss");
		return dateStr + generateId();
	}
}
