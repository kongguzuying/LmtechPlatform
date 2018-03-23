package com.lmtech.util;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.lmtech.model.JsonEntityBase;


/**
 * JsonEntity类型适配器
 * @author huang.jb
 *
 */
public class JsonEntityTypeAdapter implements JsonSerializer<JsonEntityBase>, JsonDeserializer<JsonEntityBase> {
	
	public static JsonEntityTypeAdapter DEFAULT = new JsonEntityTypeAdapter();
	
	@Override
	public JsonElement serialize(JsonEntityBase jsonEntity, Type type, JsonSerializationContext context) {
		String jsonStr = JsonUtil.simpleGson.toJson(jsonEntity);
		
		JsonParser parser = new JsonParser();
		JsonElement jsonElement = parser.parse(jsonStr);
		
		Map<String, Object> jsonAttrs = jsonEntity.getJsonAttrs();
		if (jsonAttrs != null && jsonAttrs.size() > 0) {
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			for (String key : jsonAttrs.keySet()) {
				Object value = jsonAttrs.get(key);
				if (jsonObject == null) {
					jsonObject = new JsonObject();
				}
				
				if (value != null) {
					final String valueStr = value.toString();
					if (value instanceof Boolean) {
						jsonObject.addProperty(key, Boolean.parseBoolean(valueStr));
					} else if (value instanceof Character) {
						char[] cs = value.toString().toCharArray();
						jsonObject.addProperty(key, (cs.length > 0 ? cs[0] : null));
					} else if (value instanceof Number) {
						if (value instanceof Long) {
							jsonObject.addProperty(key, Long.parseLong(valueStr));
						} else if (value instanceof Integer) {
							jsonObject.addProperty(key, Integer.parseInt(valueStr));
						} else if (value instanceof Float) {
							jsonObject.addProperty(key, Float.parseFloat(valueStr));
						} else if (value instanceof Double) {
							jsonObject.addProperty(key, Double.parseDouble(valueStr));
						} else {
							LoggerManager.warn(String.format("JSON序列化过程中，未能处理的数字类型：%1$s", value.getClass().getName()));
						}
					} else {
						jsonObject.addProperty(key, valueStr);
					}
				} else {
					jsonObject.add(key, null);
				}
			}
		}
		return jsonElement;
	}
	
	@Override
	public JsonEntityBase deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
		Object entity = JsonUtil.simpleGson.fromJson(jsonElement, type);
		if (entity == null) return null;
		
		JsonEntityBase jsonEntity = (JsonEntityBase) entity;
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		Set<Entry<String, JsonElement>> entrys = jsonObject.entrySet();
		
		Class<?> clazz = (Class<?>) type;
		for (Entry<String, JsonElement> entry : entrys) {
			String key = entry.getKey();
			Field field = null;
			try {
				field = ClassUtil.getFieldOfHierarchy(key, clazz);
			} catch (SecurityException e) {
			} catch (NoSuchFieldException e) {
			} finally {
				if (field == null) {
					//属于属性
					JsonElement valueElement = entry.getValue();
					if (valueElement.isJsonObject()) {
						LoggerManager.warn(String.format("key:%1$s为JsonObject忽略。", key));
					} else if (valueElement.isJsonArray()) {
						LoggerManager.warn(String.format("key:%1$s为JsonArray忽略。", key));
					} else if (valueElement.isJsonNull()) {
						LoggerManager.warn(String.format("key:%1$s为JsonNull忽略。", key));
					} else if (valueElement.isJsonPrimitive()) {
						JsonPrimitive value = (JsonPrimitive) entry.getValue();
						Object attrValue = null;
						if (value.isBoolean()) {
							attrValue = value.getAsBoolean();
						} else if (value.isNumber()) {
							attrValue = ConvertUtil.convertToNumber(value.getAsString());
						} else {
							attrValue = value.getAsString();
						}
						jsonEntity.setAttrValue(key, attrValue);
					}
				}
			}
		}
		return jsonEntity;
	}
	
}