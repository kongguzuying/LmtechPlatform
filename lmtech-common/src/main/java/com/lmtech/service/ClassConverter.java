package com.lmtech.service;

/**
 * 自定义类转换器
 * @author huang.jb
 *
 */
public interface ClassConverter {
	/**
	 * 将对象转换为指定类型的对象
	 * @param object
	 * @param toClass
	 * @return
	 */
	Object convertToClass(Object object, Class<?> toClass);
}
