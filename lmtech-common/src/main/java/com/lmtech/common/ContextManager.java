package com.lmtech.common;

import java.util.HashMap;
import java.util.Map;

import com.lmtech.util.IdWorkerUtil;
import com.lmtech.util.StringUtil;

/**
 * context manager
 * @author huang.jb
 *
 */
public class ContextManager {
	
	private static InheritableThreadLocal<Context> contextLocal = new InheritableThreadLocal<Context>();
	private static InheritableThreadLocal<Map<String, Object>> valueLocal = new InheritableThreadLocal<Map<String, Object>>();
	
	private static final String DATA_ACTION_TYPE_KEY = "dataActionType";
	private static final String SERIAL_NUMBER_KEY = "serialNumber";
	
	/**
	 * get context
	 * @return
	 */
	public static Context getContext() {
		Context c = contextLocal.get();
		if (c == null) {
			contextLocal.set(new Context());
		}
		return contextLocal.get();
	}
	/**
	 * set context
	 * @param context
	 */
	public static void setContext(Context context) {
		/*if (context == null) {
			throw new IllegalArgumentException("设置上下文失败，值不允许为空。");
		}
		*/
		contextLocal.set(context);
	}
	/**
	 * get value
	 * @param key
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getValue(String key) {
		if (StringUtil.isNullOrEmpty(key)) {
			throw new IllegalArgumentException("获取上下文中的值失败，KEY不允许为空。");
		}
		
		Map<String, Object> values = valueLocal.get();
		if (values != null && values.size() > 0 && values.containsKey(key)) {
			return (T) values.get(key);
		} else {
			return null;
		}
	}
	/**
	 * set value
	 * @param key
	 * @param value
	 */
	public static void setValue(String key, Object value) {
		if (StringUtil.isNullOrEmpty(key)) {
			throw new IllegalArgumentException("设置上下文中的值失败，KEY不允许为空。");
		}

		if (value != null) {
			Map<String, Object> values = valueLocal.get();
			if (values == null) {
				values = new HashMap<>();
				valueLocal.set(values);
			}
			values.put(key, value);
		}
	}
	/**
	 * remove value
	 * @param key
	 */
	public static void removeValue(String key) {
		if (StringUtil.isNullOrEmpty(key)) {
			throw new IllegalArgumentException("删除上下文中的值失败，KEY不允许为空。");
		}

		Map<String, Object> values = valueLocal.get();
		if (values != null) {
			values.remove(key);
		}
	}
	/**
	 * 清除上下文
	 */
	public static void cleanContext() {
		contextLocal.set(null);
		valueLocal.set(null);
	}
	/**
	 * 获取线程中数据操作类型
	 * @return
	 */
	public static String getDataActionType() {
		return getValue(DATA_ACTION_TYPE_KEY);
	}
	/**
	 * 设置线程中数据操作类型
	 * @param dataActionType
	 */
	public static void setDataActionType(String dataActionType) {
		setValue(DATA_ACTION_TYPE_KEY, dataActionType);
	}
	/**
	 * 构建业务流水号
	 */
	public static String buildSerialNumber() {
		String serialNumber = IdWorkerUtil.generateStringId();
		setValue(SERIAL_NUMBER_KEY, serialNumber);
		return serialNumber;
	}
	/**
	 * 清除业务流水号
	 */
	public static void cleanSerialNumber() {
		setValue(SERIAL_NUMBER_KEY, null);
	}
	/**
	 * 获取业务流水号
	 * @return
	 */
	public static String getSerialNumber() {
		return getValue(SERIAL_NUMBER_KEY);
	}
}
