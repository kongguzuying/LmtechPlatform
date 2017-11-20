package com.lmtech.common;

import com.lmtech.util.RandomUtil;

/**
 * 应用程序基础配置
 * @author huang.jb
 *
 */
public class ApplicationConfig {
	/** 发布模式 */
	public static final int MODEL_RELEASE = 0;
	/** 开发模式 */
	public static final int MODEL_DEV = 1;
	/** 测试模式 */
	public static final int MODEL_TEST = 2;
	
	/**
	 * appCode
	 */
	public static String APP_CODE = null;
	/**
	 * RMI端口号
	 */
	public static int RMI_PORT = 8113;
	/**
	 * 系统基地址
	 */
	public static String APP_URL_BASE = null;
	/**
	 * 系统WEB根目录
	 */
	public static String WEB_ROOT_PATH = null;
	/**
	 * 应用程序启动模式
	 */
	public static int MODEL = MODEL_RELEASE;
	/**
	 * 系统标识（0~5），用于生成系统关键主键，分布式部署中需确保每个的ID不一样
	 */
	public static int SYSTEM_ID = 0;
	/**
	 * 数据中心ID，用于主键生成
	 */
	public static int DATACENTER_ID = 0;
	
	/**
	 * 是否发布模式
	 * @return
	 */
	public static boolean isReleaseModel() {
		return MODEL == MODEL_RELEASE;
	}
	/**
	 * 是否开发模式
	 * @return
	 */
	public static boolean isDevModel() {
		return MODEL == MODEL_DEV;
	}
	/**
	 * 是否测试模式
	 * @return
	 */
	public static boolean isTestModel() {
		return MODEL == MODEL_TEST;
	}

	/**
	 * 初始化参数
	 */
	static {
		//随机初始化数据中心id
		DATACENTER_ID = RandomUtil.genRandomNumber(1, 15);
		//随机初始化系统ID
		SYSTEM_ID = RandomUtil.genRandomNumber(15, 31);
	}
}
