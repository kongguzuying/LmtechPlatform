package com.lmtech.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.lmtech.annotation.JsonExclude;
import com.lmtech.util.StringUtil;

/**
 * Json实体基类
 * @author huang.jb
 *
 */
public abstract class JsonEntityBase implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@JsonExclude
	private Map<String, Object> jsonAttrs;
	
	@SuppressWarnings("unchecked")
	public <T> T getAttrValueT(String key) {
		if (jsonAttrs != null && jsonAttrs.containsKey(key)) {
			return (T) jsonAttrs.get(key);
		}
		return null;
	}
	
	public void setAttrValue(String key, Object value) {
		if (StringUtil.isNullOrEmpty(key)) {
			throw new IllegalArgumentException("设置属性值失败，KEY参数不允许为空。");
		}
		
		if (jsonAttrs == null) {
			jsonAttrs = new HashMap<String, Object>();
		}
		
		jsonAttrs.put(key, value);
	}

	// property
	public Map<String, Object> getJsonAttrs() {
		return jsonAttrs;
	}

	public void setJsonAttrs(Map<String, Object> jsonAttrs) {
		this.jsonAttrs = jsonAttrs;
	}
}
