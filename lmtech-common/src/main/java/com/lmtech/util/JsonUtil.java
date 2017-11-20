package com.lmtech.util;
 

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.lmtech.annotation.JsonExclude;
import com.lmtech.model.JsonEntityBase;

/**
 * Json util
 * @author huangjb
 *
 */
public class JsonUtil {
	/** Gson，用于序列化复杂对象（默认） */
	public static Gson gson = null;
	private static GsonBuilder gb = null;
	/** 简单Gson，用于序列化简单对象 */
	public static Gson simpleGson = null;
	private static GsonBuilder simpleGb = null;
	/** 原生Gson，用于序列化基础对象 */
	public static Gson nativeGson;
	private static GsonBuilder nativeGb = null;
	
	static {
		gb = new GsonBuilder();
		gb = gb.setExclusionStrategies(DefaultExclusionStrategy.DEFAULT).setDateFormat("yyyy-MM-dd HH:mm:ss");
		gb = gb.registerTypeHierarchyAdapter(JsonEntityBase.class, JsonEntityTypeAdapter.DEFAULT);
		gson = gb.create();
		
		simpleGb = new GsonBuilder();
		simpleGb = simpleGb.setExclusionStrategies(DefaultExclusionStrategy.DEFAULT).setDateFormat("yyyy-MM-dd HH:mm:ss");
		simpleGson = simpleGb.create();
		
		nativeGb = new GsonBuilder();
		nativeGb = nativeGb.setExclusionStrategies(DefaultExclusionStrategy.DEFAULT).setDateFormat("yyyy-MM-dd HH:mm:ss");
		nativeGson = nativeGb.create();
	}
	/**
	 * 序列化对象为JSON字符串
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		return gson.toJson(obj);
	}
	/**
	 * 转换JSON字符串为对象
	 * @param jsonStr JSON字符串
	 * @param cls 对象类型
	 * @return
	 */
	public static Object fromJson(String jsonStr, Class<?> cls) {
		return gson.fromJson(jsonStr, cls);
	}
	/**
	 * 格式化JSON字符串
	 * @param jsonStr
	 * @return
	 */
	public static String formatJson(String jsonStr) {
		Gson prettyGson = gb.setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(jsonStr);
        String prettyJsonString = prettyGson.toJson(je);
        return prettyJsonString;
	}
	/**
	 * 注册特定类型适配器
	 * @param clazz 类型
	 * @param adapter 适配器
	 */
	public static void registerTypeAdapter(Class<?> clazz, Object adapter) {
		gb = gb.registerTypeAdapter(clazz, adapter);
		gson = gb.create();
	}
	/**
	 * 注册基础类型适配器
	 * @param clazz 基础类型
	 * @param adapter 适配器
	 */
	public static void registerTypeHierarchyAdapter(Class<?> clazz, Object adapter) {
		gb = gb.registerTypeHierarchyAdapter(clazz, adapter);
		gson = gb.create();
	}
	/**
	 * 注册特定类型适配器
	 * @param clazz 类型
	 * @param adapter 适配器
	 */
	public static void registerTypeAdapterForSimple(Class<?> clazz, Object adapter) {
		simpleGb = simpleGb.registerTypeAdapter(clazz, adapter);
		simpleGson = simpleGb.create();
	}
	/**
	 * 注册基础类型适配器
	 * @param clazz 基础类型
	 * @param adapter 适配器
	 */
	public static void registerTypeHierarchyAdapterForSimple(Class<?> clazz, Object adapter) {
		simpleGb = simpleGb.registerTypeHierarchyAdapter(clazz, adapter);
		simpleGson = simpleGb.create();
	}
	/**
	 * 获取Gson对象
	 * @return
	 */
	public static Gson getGson() {
		return gson;
	}
	/**
	 * 获取简单Gson对象，经过平台基础类型适配的Gson
	 * @return
	 */
	public static Gson getSimpleGson() {
		return simpleGson;
	}
	/**
	 * 获取原生Gson对象，未经过任何类型适配的Gson
	 * @return
	 */
	public static Gson getNativeGson() {
		return nativeGson;
	}
}
/**
 * 默认排除转换策略
 * @author huang.jb
 *
 */
class DefaultExclusionStrategy implements ExclusionStrategy {
	
	public static DefaultExclusionStrategy DEFAULT = new DefaultExclusionStrategy();
	
	@Override
	public boolean shouldSkipClass(Class<?> clazz) {
		return false;
	}

	@Override
	public boolean shouldSkipField(FieldAttributes fieldAttr) {
		JsonExclude jsonExclude = fieldAttr.getAnnotation(JsonExclude.class);
		return jsonExclude != null;
	}
	
}
