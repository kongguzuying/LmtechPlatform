package com.lmtech.util;
 
import java.net.URLDecoder;

/**
 * 路径工具类
 * @author huang.jb
 *
 */
public class PathUtil {
	/**
	 * 获取父路径
	 * @param path 路径
	 * @return
	 */
	public static String getParentPath(String path) {
		return getParentPath(path, 1);
	}
	/**
	 * 获取父路径
	 * @param path 路径
	 * @param level 向前推level层
	 * @return
	 */
	public static String getParentPath(String path, int level) {
		if (StringUtil.isNullOrEmpty(path)) {
			return path;
		}
		if (level <= 0) {
			return path;
		}
		
		path = path.replace("\\\\", "\\");
		path = path.replace("//", "/");
		
		int index = -1;
		index = path.lastIndexOf("\\");
		if (index < 0) {
			index = path.lastIndexOf("/");
		}
		
		if (index >= 0) {
			while (level > 0) {
				path = path.substring(0, index);
				level--;
				return getParentPath(path, level);
			}
		} else {
			return path;
		}
		
		return path;
	}
	/**
	 * 获取当前ClassPath路径
	 * @return
	 */
	public static String getCurrentClassPath() {
		 try {
			 String classpath = PathUtil.class.getResource("/").getPath();
			 classpath = URLDecoder.decode(classpath, "UTF-8");
			 if (classpath.startsWith("/")) {
				 classpath = classpath.substring(1);
			 }
			 if (classpath.endsWith("/")) {
				 classpath = classpath.substring(0, classpath.length() - 1);
			 }
			 return classpath;
		 } catch (Exception e) {
			 LoggerManager.error("获取当前类路径失败", e);
			 return null;
		 }
	}
}
