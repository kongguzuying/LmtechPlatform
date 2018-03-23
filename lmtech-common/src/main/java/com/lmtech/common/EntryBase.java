package com.lmtech.common;

import java.io.Serializable;
import java.util.Map.Entry;

/**
 * Entry基础类
 * 
 * @author huang.jb
 * 
 */
public class EntryBase<K, V> implements Entry<K, V>, Serializable {
	private static final long serialVersionUID = -8499721149061103585L;

	private final K key;
	private V value;

	public EntryBase(K key, V value) {
		this.key = key;
		this.value = value;
	}

	public EntryBase(Entry<? extends K, ? extends V> entry) {
		this.key = entry.getKey();
		this.value = entry.getValue();
	}

	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}

	public V setValue(V value) {
		V oldValue = this.value;
		this.value = value;
		return oldValue;
	}

}
