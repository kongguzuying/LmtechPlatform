package com.lmtech.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.lmtech.common.ExeResult;

/**
 * 集合工具类
 * @author huang.jb
 *
 */
public class CollectionUtil {
	
	private static final List<Cache<Object, Object>> caches = new ArrayList<Cache<Object, Object>>();
	static {
		Thread cleanUpThread = new Thread(new Runnable() {
			@Override
			public void run() {
				for (Cache<Object, Object> cache : caches) {
					cache.cleanUp();
				}
				try {
					//每30刷新一次系统缓存
					Thread.sleep(30 * 1000);
				} catch (InterruptedException e) {
					LoggerManager.error(e);
				}
			}
		});
		cleanUpThread.start();
	}
	
	/**
	 * 列表是否为NULL或空
	 * @param list
	 */
	public static boolean isNullOrEmpty(List<?> list) {
		return (list == null || list.size() <= 0);
	}
	/**
	 * 将MAP数据转为LIST数据
	 * @param map
	 * @return
	 */
	public static List<?> getMapListValue(Map<?, ?> map) {
		List<Object> result = new ArrayList<Object>();
		if (map != null && map.size() > 0) {
			for (Object item : map.values()) {
				if (item != null) {
					result.add(item);
				}
			}
		}
		return result;
	}
	/**
	 * 构建key值为String，value值为Object的map对象
	 * @param params
	 * @return
	 */
	public static Map<String, Object> buildStringObjectMap(Object... params) {
		if (params != null && params.length > 0) {
			Map<String, Object> result = new HashMap<String, Object>();
			String key = null;
			Object value = null;
			for (int i = 0; i < params.length; i++) {
				if (i % 2 == 0) {
					//key
					key = String.valueOf(params[i]);
				} else {
					//value
					value = params[i];
					result.put(key, value);
				}
			}
			return result;
		}
		return null;
	}
	/**
	 * 将对象数组转为对象列表
	 * @param objs
	 * @return
	 */
	public static List<?> convertArrayToList(Object[] objs) {
		List<Object> result = new ArrayList<Object>();
		if (objs != null && objs.length > 0) {
			for (Object item : objs) {
				result.add(item);
			}
		}
		return result;
	}
	/**
	 * 将byte列表转换为字符串列表
	 * @param bytes
	 * @return
	 */
	public static List<String> convertByteToStringList(List<byte[]> bytes) {
		List<String> result = new ArrayList<>();
		if (bytes != null && bytes.size() > 0) {
			for (byte[] item : bytes) {
				result.add(new String((item)));
			}
		}
		return result;
	}
	/**
	 * 将byteMap转换为字符串Map
	 * @param bytes
	 * @return
	 */
	public static Map<String, String> convertByteToStringMap(Map<byte[], byte[]> bytes) {
		Map<String, String> result = new HashMap<>();
		if (bytes != null && bytes.size() > 0) {
			for (byte[] key : bytes.keySet()) {
				result.put(new String(key), new String(bytes.get(key)));
			}
		}
		return result;
	}
	/**
	 * 将对象集合转为对象列表
	 * @param objs
	 * @return
	 */
	public static List<?> convertCollectionToList(Collection<?> objs) {
		List<Object> result = new ArrayList<Object>();
		if (objs != null && objs.size() > 0) {
			for (Object item : objs) {
				result.add(item);
			}
		}
		return result;
	}
	/**
	 * 拷贝列表
	 * @param objs
	 * @return
	 */
	public static <T> List<T> copyList(List<T> objs) {
		List<T> result = new ArrayList<T>();
		if (objs != null && objs.size() > 0) {
			for (T item : objs) {
				result.add(item);
			}
		}
		return result;
	}
	/**
	 * 分割列表数据为多个列表块
	 * @param ts 列表数据
	 * @param limit 每块列表数据限制大小
	 * @return
	 */
	public static <T> List<List<T>> splitList(List<T> ts, int limit) {
		if (ts == null) {
			throw new IllegalArgumentException("分割的列表不允许为空。");
		}
		if (limit <= 0) {
			throw new IllegalArgumentException("分割限制条数不允许为空。");
		}
		
		List<List<T>> blocks = new ArrayList<List<T>>();
		if (ts.size() < limit) {
			//不需要分割
			blocks.add(ts);
		} else {
			List<T> block = null;
			int i = 0, size = limit, index = 0, toIndex = size;
			while ((block = ts.subList(index, toIndex)) != null) {
				blocks.add(block);
				i++;
				index = i * size;
				toIndex = (i + 1) * size;
				
				if (toIndex >= ts.size()) {
					//最后一块数据，添加到列表中退出循环
					blocks.add(ts.subList(index, ts.size()));
					break;
				}
			}
		}
		
		return blocks;
	}
	/**
	 * 集合批次处理
	 * @param list
	 * @param handler
	 * @param batchSize
     * @param <T>
     */
	public static <T> List<ExeResult> batchHandle(List<T> list, CollectionBatchHandler<T> handler, int batchSize) {
		if (!isNullOrEmpty(list)) {
			throw new RuntimeException("集合不允许为空。");
		}
		if (handler == null) {
			throw new RuntimeException("集合批次处理接口不允许为空。");
		}
		if (batchSize <= 0) {
			throw new RuntimeException("集合分批大小必须大于0");
		}

		List<ExeResult> resultList = new ArrayList<ExeResult>();
		List<List<T>> batchList = splitList(list, batchSize);
		int batch = 1;
		for (List<T> item : batchList) {
			ExeResult result = handler.handle(item);
			if (result.isSuccess()) {
				resultList.add(result);
			} else {
				throw new RuntimeException(String.format("集合批次处理出现错误，批次：%1$d", batch));
			}
			batch++;
		}
		return resultList;
	}
	/**
	 * 获取两个集合间的差异集
	 * @param allList
	 * @param sameList
     * @return
     */
	public static List<?> getDiffOfList(List<?> allList, List<?> sameList) {
		if (isNullOrEmpty(allList)) {
			return new ArrayList<Object>();
		}
		if (isNullOrEmpty(sameList))  {
			return allList;
		}

		List<Object> diffList = new ArrayList<Object>();
		for (Object allItem : allList) {
			if (!sameList.contains(allItem)) {
				diffList.add(allItem);
			}
		}
		return diffList;
	}
	/**
	 * 构建一个带有超时机制的缓存Map
	 * @param timeout
	 * @param timeUnit
	 * @return
	 */
	public static ConcurrentMap<Object, Object> buildTimeoutMap(int timeout, TimeUnit timeUnit) {
		return buildTimeoutMap(timeout, timeUnit, null);
	}
	/**
	 * 构建一个带有超时机制的缓存Map
	 * @param timeout
	 * @param timeUnit
	 * @param removalListener
	 * @return
	 */
	public static ConcurrentMap<Object, Object> buildTimeoutMap(int timeout, TimeUnit timeUnit, RemovalListener<Object, Object> removalListener) {
		return buildTimeoutMapAfterAccess(timeout, timeUnit, removalListener);
	}
	/**
	 * 构建一个带有超时机制的缓存Map
	 * @param timeout
	 * @param timeUnit
	 * @param removalListener
	 * @return
	 */
	public static ConcurrentMap<Object, Object> buildTimeoutMapAfterAccess(int timeout, TimeUnit timeUnit, RemovalListener<Object, Object> removalListener) {
		CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
		cacheBuilder.expireAfterAccess(timeout, timeUnit);
		if (removalListener != null) {
			cacheBuilder.removalListener(removalListener);
		}
		return buildTimeoutMap(cacheBuilder);
	}
	/**
	 * 构建一个带有超时机制的缓存Map
	 * @param timeout
	 * @param timeUnit
	 * @param removalListener
	 * @return
	 */
	public static ConcurrentMap<Object, Object> buildTimeoutMapAfterWrite(int timeout, TimeUnit timeUnit, RemovalListener<Object, Object> removalListener) {
		CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
		cacheBuilder.expireAfterWrite(timeout, timeUnit);
		if (removalListener != null) {
			cacheBuilder.removalListener(removalListener);
		}
		return buildTimeoutMap(cacheBuilder);
	}
	
	private static ConcurrentMap<Object, Object> buildTimeoutMap(CacheBuilder<Object, Object> cacheBuilder) {
		final Cache<Object, Object> cache = cacheBuilder.build();
		//加入缓存可以定时清理过期的数据
		caches.add(cache);
		ConcurrentMap<Object, Object> map = new ConcurrentMap<Object, Object>() {
			private ConcurrentMap<Object, Object> cacheMap = cache.asMap();
			@Override
			public int size() {
				cache.cleanUp();
				return (int) cache.size();
			}
			@Override
			public boolean isEmpty() {
				return cacheMap.isEmpty();
			}
			@Override
			public boolean containsKey(Object key) {
				return cacheMap.containsKey(key);
			}
			@Override
			public boolean containsValue(Object value) {
				return cacheMap.containsValue(value);
			}
			@Override
			public Object get(Object key) {
				return cacheMap.get(key);
			}
			@Override
			public Object put(Object key, Object value) {
				return cacheMap.put(key, value);
			}
			@Override
			public Object remove(Object key) {
				return cacheMap.remove(key);
			}
			@Override
			public void putAll(Map<? extends Object, ? extends Object> m) {
				cacheMap.putAll(m);
			}
			@Override
			public void clear() {
				cacheMap.clear();
			}
			@Override
			public Set<Object> keySet() {
				return cacheMap.keySet();
			}
			@Override
			public Collection<Object> values() {
				return cacheMap.values();
			}
			@Override
			public Set<java.util.Map.Entry<Object, Object>> entrySet() {
				return cacheMap.entrySet();
			}
			@Override
			public Object putIfAbsent(Object key, Object value) {
				return cacheMap.putIfAbsent(key, value);
			}
			@Override
			public boolean remove(Object key, Object value) {
				return cacheMap.remove(key, value);
			}
			@Override
			public boolean replace(Object key, Object oldValue, Object newValue) {
				return cacheMap.replace(key, oldValue, newValue);
			}
			@Override
			public Object replace(Object key, Object value) {
				return cacheMap.replace(key, value);
			}
		};
		return map;
	}
	
}
