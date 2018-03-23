package com.lmtech.util;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Properties;

/**
 * property util
 * @author huangjb
 *
 */
public class PropertyUtil {
	
	private static Properties props = new Properties();
	
	/**
	 * init properties
	 * @param location
	 */
	public static void loadProperties(String location) {
		if (location == null || location.equals("")) return;
		try {
			Properties prop1 = new Properties();
			File file = new File(location);
			if (file.exists()) {
				//从文件中读取
				prop1.load(new FileReader(file));
			} else {
				//从classpath中读取
				InputStream is = PropertyUtil.class.getClassLoader().getResourceAsStream("./" + location);
				if (is != null) {
					prop1.load(is);
					is.close();
				}
			}
			
			for (Object key : prop1.keySet()) {
				Object value = prop1.get(key);
				props.setProperty(key.toString(), value.toString());
			}
		} catch (Exception e) {
			LoggerManager.error(e);
		}
	}
	
	/**
	 * init properties
	 * @param locations
	 */
	public static void loadProperties(String[] locations) {
		if (locations == null || locations.length <= 0) return;
		for (String location : locations) {
			loadProperties(location);
		}
	}
	
	/**
	 * get property
	 * @param key
	 * @return
	 */
	public static String getProperty(String key) {
		if (props.containsKey(key)) {
			return props.getProperty(key);
		} else {
			return null;
		}
	}

	/**
	 * 获取属性long值
	 * @param key
	 * @return
     */
	public static long getPropertyOfLong(String key) {
		String value = getProperty(key);
		if (!StringUtil.isNullOrEmpty(value)) {
			return Long.parseLong(value);
		} else {
			throw new RuntimeException(String.format("未找到键为：%1$s的配置项。", key));
		}
	}

	/**
	 * 获取属性long值
	 * @param key
	 * @param defaultValue
     * @return
     */
	public static long getPropertyOfLong(String key, long defaultValue) {
		String value = getProperty(key);
		if (!StringUtil.isNullOrEmpty(value)) {
			return Long.parseLong(value);
		} else {
			return defaultValue;
		}
	}

	/**
	 * 获取属性int值
	 * @param key
	 * @return
	 */
	public static int getPropertyOfInt(String key) {
		String value = getProperty(key);
		if (!StringUtil.isNullOrEmpty(value)) {
			return Integer.parseInt(value);
		} else {
			throw new RuntimeException(String.format("未找到键为：%1$s的配置项。", key));
		}
	}

	/**
	 * 获取属性int值
	 * @param key
	 * @param defaultValue
     * @return
     */
	public static int getPropertyOfInt(String key, int defaultValue) {
		String value = getProperty(key);
		if (!StringUtil.isNullOrEmpty(value)) {
			return Integer.parseInt(value);
		} else {
			return defaultValue;
		}
	}

	/**
	 * 销毁
	 */
	public static void destroy() {
		props.clear();
		props = null;
	}
}
