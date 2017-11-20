package com.lmtech.common;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 基于TreeMap实现的KEY和VALUE都保持唯一性的TreeMap
 * @author huang.jb
 *
 * @param <K>
 * @param <V>
 */
public class TreeBiMap<K, V> extends TreeMap<K, V> {

	private static final long serialVersionUID = 1L;
	
	/** 与当前map相反的键值对 */
	private HashMap<V, K> invertMap = new HashMap<V, K>();

	public K getByValue(V value) {
		return invertMap.get(value);
	}
	
	public K removeByValue(V value) {
		K key = this.getByValue(value);
		super.remove(key);
		return invertMap.remove(value);
	}
	
	@Override
	public void putAll(Map<? extends K, ? extends V> map) {
		throw new RuntimeException("NOT SUPPORT");
	}

	@Override
	public V put(K key, V value) {
		//检查value一致性
		if (!invertMap.containsKey(value)) {
			invertMap.put(value, key);
		} else {
			throw new RuntimeException("已包含value为：" + value + "的项。");
		}
		return super.put(key, value);
	}

	@Override
	public V remove(Object key) {
		V value = super.get(key);
		invertMap.remove(value);
		return super.remove(key);
	}

	@Override
	public java.util.Map.Entry<K, V> pollFirstEntry() {
		throw new RuntimeException("NOT SUPPORT");
	}

	@Override
	public java.util.Map.Entry<K, V> pollLastEntry() {
		throw new RuntimeException("NOT SUPPORT");
	}
}
