package com.lmtech.util;
 
import java.io.File;
import java.io.FileFilter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.lmtech.util.LoggerManager;

/**
 * Class工具类
 * @author hjb
 *
 */
public class ClassUtil {
	/**
	 * 获取泛型类的泛型类型全名
	 * @param cls
	 * @return
	 */
	public static String getGenericClassName(Class<?> cls) {
		try {
			ParameterizedType pType = (ParameterizedType)cls.getGenericSuperclass();
			Type[] actTypes = pType.getActualTypeArguments();
			if (actTypes.length > 0) {
				String typeStr = actTypes[0].toString().split(" ")[1];
				return Class.forName(typeStr).getName();
			}
		} catch (Exception e) {
			LoggerManager.error("获取类名出现错误", e);
		}
		return null;
	}
	/**
	 * 获取泛型类
	 * @param clazz
	 * @return
	 */
	public static Class getGenericClass(Class clazz) {
		try {
			ParameterizedType pType = (ParameterizedType)clazz.getGenericSuperclass();
			Type[] actTypes = pType.getActualTypeArguments();
			if (actTypes.length > 0) {
				String className = actTypes[0].getTypeName();
				return getClass(className);
			}
		} catch (Exception e) {
			LoggerManager.error("获取泛型类出现错误", e);
		}
		return null;
	}
	/**
	 * 获取泛型类
	 * @param clazz
	 * @param index
	 * @return
	 */
	public static Class getGenericClass(Class clazz, int index) {
		try {
			ParameterizedType pType = (ParameterizedType)clazz.getGenericSuperclass();
			Type[] actTypes = pType.getActualTypeArguments();
			if (actTypes.length > 0 && index < actTypes.length) {
				String className = actTypes[index].getTypeName();
				return getClass(className);
			}
		} catch (Exception e) {
			LoggerManager.error("获取泛型类出现错误", e);
		}
		return null;
	}
	/**
	 * 获取泛型类列表
	 * @param clazz
	 * @return
	 */
	public static List<Class> getGenericClassList(Class clazz) {
		List<Class> result = new ArrayList<>();
		try {
			ParameterizedType pType = (ParameterizedType)clazz.getGenericSuperclass();
			Type[] actTypes = pType.getActualTypeArguments();
			if (actTypes.length > 0) {
				for (int i = 0; i < actTypes.length; i++) {
					String className = actTypes[i].getTypeName();
					Class indexClass = getClass(className);
					result.add(indexClass);
				}
			}
		} catch (Exception e) {
			LoggerManager.error("获取泛型类列表出现错误", e);
		}
		return result;
	}
	/**
	 * 获取泛型类的泛型类型类名
	 * @param cls
	 * @return
	 */
	public static String getGenericSimpleClassName(Class<?> cls) {
		String classFullName = getGenericClassName(cls);
		if (classFullName.indexOf('.') > 0) {
			return classFullName.substring(classFullName.lastIndexOf('.'));
		} else {
			return null;
		}
	}
	/**
	 * 获取类对象 
	 * @param className
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static Object getClassObject(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class<?> cla = Class.forName(className);
		return cla.newInstance();
	}
	/**
	 * 获取对象类全名
	 * @param obj
	 * @return
	 */
	public static String getClassName(Object obj) {
		if (obj != null) {
			return obj.getClass().getName();
		} else {
			return null;
		}
	}
	/**
	 * 获取类全名
	 * @param cls
	 * @return
	 */
	public static String getClassName(Class<?> cls) {
		if (cls != null) {
			return cls.getName();
		} else {
			return null;
		}
	}
	/**
	 * 获取对象类名
	 * @param obj
	 * @return
	 */
	public static String getSimpleClassName(Object obj) {
		if (obj != null) {
			return obj.getClass().getClass().getSimpleName();
		} else {
			return null;
		}
	}
	/**
	 * 获取类名
	 * @param cls
	 * @return
	 */
	public static String getSimpleClassName(Class<?> cls) {
		if (cls != null) {
			return cls.getSimpleName();
		} else {
			return null;
		}
	}
	/**
	 * 获取类的接口列表
	 * @param cls
	 * @return
	 */
	public static Class<?>[] getClassInterfaces(Class<?> cls) {
		if (cls != null) {
			return cls.getInterfaces();
		}
		return null;
	}
	/**
	 * 获取类的接口
	 * @param cls
	 * @return
	 */
	public static Class<?> getClassInterface(Class<?> cls) {
		if (cls != null) {
			Class<?>[] clses = cls.getInterfaces();
			if (clses != null && clses.length > 0) {
				return clses[0];
			}
		}
		return null;
	}
	/**
	 * 获取类对象
	 * @param className
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static Class<?> getClass(String className) throws ClassNotFoundException {
		return Class.forName(className);
	}
	/**
	 * 获取类的所有父类
	 * @param clazz
	 * @return
	 */
	public static List<Class<?>> getAllSuperClass(Class<?> clazz) {
		if (clazz == null) {
			throw new IllegalArgumentException("类对象不允许为空。");
		}
		
		List<Class<?>> clazzs = new ArrayList<Class<?>>();
		Class<?> superClass = (Class<?>) clazz.getGenericSuperclass();
		while (superClass != null) {
			clazzs.add(superClass);
			superClass = (Class<?>) superClass.getGenericSuperclass();
		}
		return clazzs;
	}
	/**
	 * 在所有类层级中获取字段
	 * @param fieldName
	 * @param clazz
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	public static Field getFieldOfHierarchy(String fieldName, Class<?> clazz) throws SecurityException, NoSuchFieldException {
		if (StringUtil.isNullOrEmpty(fieldName)) {
			throw new IllegalArgumentException("字段名称不允许为空。");
		}
		if (clazz == null) {
			throw new IllegalArgumentException("类型clazz不允许为空。");
		}
		
		Field field = null;
		try {
			field = clazz.getDeclaredField(fieldName);
		} catch (SecurityException e) {
			throw e;
		} catch (NoSuchFieldException e) {
		} finally {
			if (field == null) {
				List<Class<?>> superClasses = getAllSuperClass(clazz);
				for (Class<?> superClass : superClasses) {
					try {
						field = superClass.getDeclaredField(fieldName);
						if (field != null) {
							break;
						}
					} catch (SecurityException e) {
						throw e;
					} catch (NoSuchFieldException e) {
					}
				}
			}
		}
		
		if (field == null) {
			throw new NoSuchFieldException();
		} else {
			return field;
		}
	}
	/**
	 * 在所有类层级中获取所有可用字段
	 * @param clazz
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	public static List<Field> getFieldsOfHierarchy(Class<?> clazz) throws SecurityException {
		if (clazz == null) {
			throw new IllegalArgumentException("类型clazz不允许为空。");
		}
		
		List<Field> fields = new ArrayList<Field>();
		try {
			for (Field field : clazz.getDeclaredFields()) {
				fields.add(field);
			}
		} catch (SecurityException e) {
			throw e;
		} finally {
			List<Class<?>> superClasses = getAllSuperClass(clazz);
			for (Class<?> superClass : superClasses) {
				try {
					for (Field field : superClass.getDeclaredFields()) {
						fields.add(field);
					}
				} catch (SecurityException e) {
					throw e;
				}
			}
		}
		
		return fields;
	}
	/**
	 * 获取对象属性值
	 * @param obj
	 * @param fieldName
	 * @return
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Object getFieldValue(Object obj, String fieldName) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		if (obj != null) {
			Field field = getFieldOfHierarchy(fieldName, obj.getClass());
			field.setAccessible(true);
			return field.get(obj);
		} else {
			return null;
		}
	}
	/**
	 * 获取对象属性值列表
	 * @param obj
	 * @return
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Map<String, Object> getFieldValues(Object obj) throws SecurityException, IllegalArgumentException, IllegalAccessException {
		Map<String, Object> fieldValues = new HashMap<String, Object>();
		if (obj != null) {
			List<Field> fields = getFieldsOfHierarchy(obj.getClass());
			for (Field field : fields) {
				field.setAccessible(true);
				Object fieldValue = field.get(obj);
				if (fieldValue != null) {
					fieldValues.put(field.getName(), fieldValue);
				}
			}
		}
		return fieldValues;
	}
	/**
	 * src是否是obj的实例
	 * @param obj
	 * @param instance
	 * @return
	 */
	public static boolean isSameType(Object obj, Class<?> instance) {
		return obj instanceof Class;
	}
	/**
	 * 获取实现接口所有的类
	 * @param clazz 接口类
	 * @param packageName 需要搜索的包名 
	 * @return
	 */
	public static List<Class<?>> getAllClassByInterface(Class<?> clazz, String packageName) {
		List<Class<?>> result = new ArrayList<Class<?>>();
		if (clazz.isInterface() && !StringUtil.isNullOrEmpty(packageName)) {
			List<Class<?>> classes = getClassesOfPackage(packageName);
			if (classes != null && classes.size() > 0) {
				for (Class<?> c : classes) {
					if (clazz.isAssignableFrom(c)) {
						if (!c.equals(clazz)) {
							result.add(c);
						}
					}
				}
			}
		}
		return result;
	}
	/**
	 * 根据方法名获取类中的方法，如果有重载方法时只取第一个
	 * @param clazz
	 * @param methodName
	 * @return
	 */
	public static Method getMethod(Class<?> clazz, String methodName) {
		Method[] methods = clazz.getMethods();
		if (methods != null && methods.length > 0) {
			for (Method method : methods) {
				if (method.getName().equals(methodName)) {
					return method;
				}
			}
		}
		return null;
	}
	/**
	 * 根据方法名获取类中的方法，如果有重载方法时取出所有重载的方法
	 * @param clazz
	 * @param methodName
	 * @return
	 */
	public static List<Method> getMethods(Class<?> clazz, String methodName) {
		List<Method> result = new ArrayList<Method>();
		Method[] methods = clazz.getMethods();
		if (methods != null && methods.length > 0) {
			for (Method method : methods) {
				if (method.getName().equals(methodName)) {
					result.add(method);
				}
			}
		}
		return result;
	}
	/**
	 * 动态地执行对象的方法
	 * @param invoker
	 * @param methodName
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static Object invokeMethod(Object invoker, String methodName, Object[] params) throws Exception {
		if (invoker == null) {
			throw new IllegalArgumentException("执行的对象不允许为空");
		}
		if (StringUtil.isNullOrEmpty(methodName)) {
			throw new IllegalArgumentException("方法名不允许为空");
		}
		
		Method method = getMethod(invoker.getClass(), methodName);
		return method.invoke(invoker, params);
	}
	/**
	 * 设置属性值，属性必须包含set方法
	 * @param t
	 * @param fieldName
	 * @param value
	 */
	public static void setFieldValue(Object t, String fieldName, Object value) {
		try {
			Class<?> clazz = t.getClass();
			Field field = getFieldOfHierarchy(fieldName, clazz);
			field.setAccessible(true);
			field.set(t, value);
		} catch (Exception e) {
			throw new RuntimeException(String.format("设置属性%1$s值失败。", fieldName), e);
		}
	}
	/**
	 * 如果属性存在则设置属性值，属性必须包含set方法
	 * @param t
	 * @param fieldName
	 * @param value
	 */
	public static void setFieldValueIfExist(Object t, String fieldName, Object value) {
		if (t == null || value == null) {
			return;
		}
		
		Class<?> clazz = t.getClass();
		try {
			Field field = getFieldOfHierarchy(fieldName, clazz);
			field.setAccessible(true);
			//特殊值处理
			Class<?> fieldClass = (Class<?>) field.getGenericType();
			if (!fieldClass.equals(value.getClass())) {
				//类型不匹配，需要转换
				Object convertedValue = ConvertUtil.convertToClass(value, fieldClass);
				field.set(t, convertedValue);
			} else {
				field.set(t, value);
			}
		} catch (NoSuchFieldException e) {
			//不存在属性值则忽略
		} catch (Exception e) {
			throw new RuntimeException(String.format("设置属性%1$s值失败。", fieldName), e);
		}
	}
	/**
	 * 是否基础数据类型
	 * @param object
	 * @return
	 */
	public static boolean isBasicType(Object object) {
		if (object == null) {
			return false;
		}
		
		if (object instanceof Integer ||
				object instanceof Double ||
				object instanceof Short ||
				object instanceof Long ||
				object instanceof Float ||
				object instanceof Boolean ||
				object instanceof String) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 创建类的新实例
	 * @param clazz
	 * @return
     */
	public static Object newInstance(Class<?> clazz) {
		if (clazz == null) {
			throw new IllegalArgumentException("class should not be null.");
		}

		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 是否有注解
	 * @param beanType
	 * @param annotationType
	 * @return
	 */
	public static boolean hasAnnotation(Class<?> beanType, Class<? extends Annotation> annotationType) {
		Annotation[] annotation = beanType.getDeclaredAnnotationsByType(annotationType);
		return annotation != null && annotation.length > 0;
	}
	
	private static List<Class<?>> getClassesOfPackage(String packageName) {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		String packageDirName = packageName.replace(".", "/");
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
			while (dirs.hasMoreElements()) {
				URL url = dirs.nextElement();
				String protocol = url.getProtocol();
				if (protocol.equals("file")) {
					//获取物理路径包的路径
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					//以文件方式扫描整个包下的文件，并添加到集合中
					findAndAddClassesInPackageByFile(packageName, filePath, classes);
				} else if (protocol.equals("jar")) {
					//JAR文件
					JarFile jar;
					try {
						jar = ((JarURLConnection) url.openConnection()).getJarFile();
						Enumeration<JarEntry> entries = jar.entries();
						while (entries.hasMoreElements()) {
							JarEntry entry = entries.nextElement();
							String name = entry.getName();
							if (name.charAt(0) == '/') {
								name = name.substring(1);
							}
							//如果前半部分和定义的包名相同
							if (name.startsWith(packageDirName)) {
								int idx = name.lastIndexOf('/');
								//如果以/结尾是一个包
								if (idx != -1) {
									//获取包名 把/替换成.
									packageName = name.substring(0, idx).replace('/', '.');
								}
								//如果可以迭代下去，并且是一个包
								if (idx != -1) {
									//如果是一个class文件 而且不是目录
									if (name.endsWith(".class") && !entry.isDirectory()) {
										//去掉.class后缀，获取真正的类名
										String className = name.substring(packageName.length() + 1, name.length() - 6);
										try {
											classes.add(Class.forName(packageName + "." + className));
										} catch (ClassNotFoundException e) {
											LoggerManager.error(e);
										}
									}
								}
							}
						}
					} catch (Exception e) {
						LoggerManager.error(e);
					}
				}
			}
		} catch (Exception e) {
			LoggerManager.error(e);
		}
		
		return classes;
	}
	
	private static void findAndAddClassesInPackageByFile(String packageName, String packagePath, List<Class<?>> classes) {
		File dir = new File(packagePath);
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		
		File[] dirFiles = dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isDirectory() || file.getName().endsWith(".class");
			}
		});
		
		for (File file : dirFiles) {
			if (file.isDirectory()) {
				findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), classes); 
			} else {
				String className = file.getName().substring(0, file.getName().length() - 6);
				try {
					classes.add(Class.forName(packageName + "." + className));
				} catch (ClassNotFoundException e) {
					LoggerManager.error(e);
				}
			}
		}
	}
}
