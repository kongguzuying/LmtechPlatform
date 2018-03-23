package com.lmtech.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.lmtech.service.ClassConverter;

/**
 * 类型转换工具类
 * @author hjb
 *
 */
public class ConvertUtil {
	
	private static Map<String, ClassConverter> classConverters = new ConcurrentHashMap<String, ClassConverter>();
	
	/**
	 * 将对象转换成字符串
	 * @param obj
	 * @return
	 */
	public static String convertToString(Object obj) {
		if (obj == null) return null;
		
		String objName = obj.getClass().getSimpleName();
		String objFullName = obj.getClass().getName();
		if (isBasicType(obj)) {
			if (objName.equals("Integer")) {
				int intObj = Integer.parseInt(obj.toString());
				return String.valueOf(intObj);
			} else if (objName.equals("Long")) {
				long longObj = Long.parseLong(obj.toString());
				return String.valueOf(longObj);
			} else if (objName.equals("Float")) {
				float floatObj = Float.parseFloat(obj.toString());
				return String.valueOf(floatObj);
			} else if (objName.equals("Double")) {
				double doubleObj = Double.parseDouble(obj.toString());
				return String.valueOf(doubleObj);
			} else if (objName.equals("Boolean")) {
				boolean booleanObj = Boolean.parseBoolean(obj.toString());
				return String.valueOf(booleanObj);
			} else if (objName.equals("String")) {
				return obj.toString();
			} else if (objName.equals("Byte")) {
				byte byteObj = Byte.parseByte(obj.toString());
				return String.valueOf(byteObj);
			} else if (objName.equals("Character")) {
				char[] charArray = obj.toString().toCharArray();
				return String.valueOf(charArray);
			} else if (objFullName.equals("java.util.Date")) {
				Date dateObj = (Date)obj;
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				return dateFormat.format(dateObj);
			}
			else {
				return null;
			}
		} else {
			return obj.toString();
		}
	}
	/**
	 * 是否基础类型
	 * @param obj
	 * @return
	 */
	public static boolean isBasicType(Object obj) {
		Class<?> objClass = obj.getClass();
		return isBasicType(objClass);
	}
	/**
	 * 是否基础类型
	 * @param clazz
	 * @return
	 */
	public static boolean isBasicType(Class<?> clazz) {
		return 
			clazz.equals(Integer.class) ||
			clazz.equals(int.class) ||
			clazz.equals(Long.class) ||
			clazz.equals(long.class) ||
			clazz.equals(Float.class) ||
			clazz.equals(float.class) ||
			clazz.equals(Double.class) ||
			clazz.equals(double.class) ||
			clazz.equals(Boolean.class) ||
			clazz.equals(boolean.class) ||
			clazz.equals(String.class) ||
			clazz.equals(Byte.class) ||
			clazz.equals(byte.class) ||
			clazz.equals(Character.class) ||
			clazz.equals(char.class) ||
			clazz.equals(Date.class);
	}
	/**
	 * 将对象转换成Long
	 * @param object
	 * @return
	 */
	public static long convertToLong(Object object) {
		if (object != null) {
			String value = object.toString().trim();
			int index = value.indexOf(".");
			if (index > 0) {
				value = value.substring(0, index);
			}
			return Long.parseLong(value);
		}
		return 0;
	}
	/**
	 * 将对象转换成int
	 * @param object
	 * @return
	 */
	public static int convertToInt(Object object) {
		if (object != null) {
			String value = object.toString().trim();
			int index = value.indexOf(".");
			if (index > 0) {
				value = value.substring(0, index);
			}
			return Integer.parseInt(value);
		}
		return 0;
	}
	/**
	 * 将对象转换成short
	 * @param object
	 * @return
	 */
	public static int convertToShort(Object object) {
		if (object != null) {
			String value = object.toString().trim();
			int index = value.indexOf(".");
			if (index > 0) {
				value = value.substring(0, index);
			}
			return Short.parseShort(value);
		}
		return 0;
	}
	/**
	 * 将对象转换成double
	 * @param object
	 * @return
	 */
	public static double convertToDouble(Object object) {
		if (object != null) {
			return Double.parseDouble(object.toString().trim());
		}
		return 0;
	}
	/**
	 * 将对象转换成float
	 * @param object
	 * @return
	 */
	public static float convertToFloat(Object object) {
		if (object != null) {
			return Float.parseFloat(object.toString().trim());
		}
		return 0;
	}
	/**
	 * 转换成数值型
	 * @param object
	 * @return
	 */
	public static Object convertToNumber(Object object) {
		if (object != null) {
			try {
				String number = object.toString().trim();
				if (number.indexOf(".") >= 0) {
					//转成double型
					return Double.parseDouble(number);
				} else {
					long l = Long.parseLong(number);
					if (l > Integer.MAX_VALUE || l < Integer.MIN_VALUE) {
						return l;
					} else {
						return (int) l;
					}
				}
			} catch (Exception e) {
				LoggerManager.error(e);
				return 0;
			}
		}
		return 0;
	}
	/**
	 * 将对象转换成boolean
	 * @param object
	 * @return
	 */
	public static boolean convertToBoolean(Object object) {
		if (object != null) {
			try {
				return Boolean.parseBoolean(object.toString().trim());
			} catch (Exception e) {
				LoggerManager.error(e);
				return false;
			}
		}
		return false;
	}
	/**
	 * 将对象转换成byte
	 * @param object
	 * @return
	 */
	public static byte convertToByte(Object object) {
		return Byte.parseByte(object.toString().trim());
	}
	/**
	 * 将对象转换成任意类型，如果无法识别的类型则抛出异常
	 * @param object
	 * @param toClass
	 * @return
	 */
	public static Object convertToClass(Object object, Class<?> toClass) {
		if (object != null) {
			if (object.getClass().equals(toClass)) {
				return object;
			} else {
				if (isBasicType(toClass)) {
					if (toClass.equals(Integer.class) || toClass.equals(int.class)) {
						return convertToInt(object);
					} else if (toClass.equals(Long.class) || toClass.equals(long.class)) {
						return convertToLong(object);
					} else if (toClass.equals(Float.class) || toClass.equals(float.class)) {
						return convertToFloat(object);
					} else if (toClass.equals(Double.class) || toClass.equals(double.class)) {
						return convertToDouble(object);
					} else if (toClass.equals(Boolean.class) || toClass.equals(boolean.class)) {
						return convertToBoolean(object);
					} else if (toClass.equals(String.class)) {
						return convertToString(object);
					} else if (toClass.equals(Byte.class) || toClass.equals(byte.class)) {
						return convertToByte(object);
					} else if (toClass.equals(Date.class)) {
						return DateUtil.parse(object.toString(), "yyyy-MM-dd HH:mm:ss");
					} else {
						throw new RuntimeException(String.format("类型转换失败，无法识别的类型：%1$s", toClass.getName()));
					}
				} else {
					String className = toClass.getName();
					if (classConverters.containsKey(className)) {
						//自定类转换器
						ClassConverter converter = classConverters.get(className);
						return converter.convertToClass(object, toClass);
					} else {
						throw new RuntimeException(String.format("类型转换失败，无法识别的类型：%1$s", toClass.getName()));
					}
				}
			}
		} else {
			return null;
		}
	}
	/**
	 * 注册自定义类转换器
	 * @param clazz
	 * @param converter
	 */
	public static void registerClassConverter(Class<?> clazz, ClassConverter converter) {
		if (clazz == null) {
			throw new IllegalArgumentException("类型不允许为空");
		}
		if (converter == null) {
			throw new IllegalArgumentException("类型转换器不允许为空");
		}
		
		String className = clazz.getName();
		classConverters.put(className, converter);
	}
}
