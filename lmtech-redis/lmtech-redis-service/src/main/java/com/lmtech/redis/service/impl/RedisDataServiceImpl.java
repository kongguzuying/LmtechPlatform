package com.lmtech.redis.service.impl;

import com.lmtech.common.PageData;
import com.lmtech.redis.constants.RedisConstants;
import com.lmtech.redis.constants.RedisQueueStatus;
import com.lmtech.redis.exception.RedisJsonParseException;
import com.lmtech.redis.service.RedisDataService;
import com.lmtech.util.CollectionUtil;
import com.lmtech.util.JsonUtil;
import com.lmtech.util.LoggerManager;
import com.lmtech.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RefreshScope
public class RedisDataServiceImpl implements RedisDataService {

	@Value("${redis.ip}")
	private String redisIpAddress;
	@Value("${redis.port}")
	private String redisPort;
	@Value("${redis.auth}")
	private String auth;

	private String REDIS_KEY_PREFIX = "ea_card_crm:";

	@Override
	public String getKey(String key) {
		Jedis client = this.getJedis();
		return client.get(getRedisKey(key));
	}

	@Override
	public <T> T getKeyObject(String key, Class<?> type) {
		return getKeyObject(getRedisKey(key), type, false);
	}

	@Override
	public <T> T getKeyObject(String key, Class<?> type, boolean removeFailed) {
		String value = this.getKey(getRedisKey(key));
		if (!StringUtil.isNullOrEmpty(value)) {
			try {
				Object object = JsonUtil.fromJson(value, type);
				return (T) object;
			} catch (Exception e) {
				LoggerManager.error(e);
				throw new RedisJsonParseException(value, type.getName());
			} finally {
				if (removeFailed) {
					this.removeKey(getRedisKey(key));
				}
			}
		} else {
			return null;
		}
	}

	@Override
	public List<String> getKeys(List<String> keys) {
		List<String> result = new ArrayList<String>();
		if (CollectionUtil.isNullOrEmpty(keys)) {
			return result;
		}

		Jedis client = this.getJedis();
		Pipeline pipeline = client.pipelined();
		for (String key : keys) {
			pipeline.get(getRedisKey(key));
		}
		List<Object> values = pipeline.syncAndReturnAll();
		for (Object value : values) {
			result.add(String.valueOf(value));
		}
		return result;
	}

	@Override
	public boolean setKey(String key, String value) {
		Jedis client = this.getJedis();
		client.set(getRedisKey(key), value);
		return true;
	}

	@Override
	public boolean setKeyObject(String key, Object object) {
		String json = JsonUtil.toJson(object);
		return this.setKey(getRedisKey(key), json);
	}

	@Override
	public boolean setKey(String key, String value, String nxxx, long expireTime) {
		if (expireTime <= 0) {
			throw new RuntimeException("redis expire time must > 0");
		}
		Jedis client = this.getJedis();
		client.set(getRedisKey(key), value, nxxx, "EX"/*seconds*/, expireTime);
		return true;
	}

	@Override
	public boolean setKeyObject(String key, Object object, String nxxx, long expireTime) {
		String json = JsonUtil.toJson(object);
		return this.setKey(getRedisKey(key), json, nxxx, expireTime);
	}

	@Override
	public void removeKey(String key) {
		Jedis client = this.getJedis();
		client.del(getRedisKey(key));
	}

	@Override
	public void removeKeys(List<String> keys) {
		Jedis client = this.getJedis();
		Pipeline pipeline = client.pipelined();
		for (String key : keys) {
			pipeline.del(getRedisKey(key));
		}
		pipeline.syncAndReturnAll();
	}

	@Override
	public void setExpireTime(String key, int seconds) {
		Jedis client = this.getJedis();
		client.expire(getRedisKey(key), seconds);
	}

	@Override
	public Map<String, String> get(String tableName, String id) {
		String key = this.getStoreKey(tableName, id);
		Jedis client = this.getJedis();
		return client.hgetAll(getRedisKey(key));
	}

	@Override
	public List<Map<String, String>> getAll(String tableName) {
		Jedis client = this.getJedis();
		if (client.exists(tableName)) {
			long len = client.llen(tableName);
			List<String> ids = client.lrange(tableName, 0, len);
			Pipeline pipeline = client.pipelined();
			for (String id : ids) {
				String key = this.getStoreKey(tableName, id);
				pipeline.hgetAll(getRedisKey(key));
			}
			List<Object> allResults = pipeline.syncAndReturnAll();
			
			List<Map<String, String>> result = new ArrayList<Map<String, String>>();
			if (allResults != null && allResults.size() > 0) {
				for (Object item : allResults) {
					result.add((Map<String, String>) item);
				}
			}
			return result;
		} else {
			return new ArrayList<Map<String, String>>();
		}
	}

	@Override
	public PageData getPageData(String tableName, int pageIndex, int pageSize) {
		return null;
	}

	@Override
	public void addOrUpdate(String tableName, String id, Map<String, String> entity) {
		String key = this.getStoreKey(tableName, id);
		Jedis client = this.getJedis();
		
		boolean isAdd = false;
		if (!client.exists(getRedisKey(key))) {
			isAdd = true;
		}
		Pipeline pipelien = client.pipelined();
		//写入行数据
		for (String item : entity.keySet()) {
			String value = entity.get(item);
			pipelien.hset(getRedisKey(key), item, (value != null ? value : ""));
		}
		if (isAdd) {
			//如果添加行，将表的行id写入列表中
			pipelien.lpush(tableName, id);
		}
		pipelien.sync();
	}
	
	public void addOrUpdateMany(String tableName, Map<String, Map<String, String>> entitys) {
		Jedis client = this.getJedis();
		Pipeline pipelien = client.pipelined();
		
		//清空行数据
		pipelien.ltrim(tableName, Integer.MAX_VALUE, Integer.MAX_VALUE);
		for (String id : entitys.keySet()) {
			String key = this.getStoreKey(tableName, id);
			
			Map<String, String> entity = entitys.get(id);
			//写入行数据
			for (String item : entity.keySet()) {
				String value = entity.get(item);
				pipelien.hset(getRedisKey(key), item, (value != null ? value : ""));
			}
			pipelien.lpush(tableName, id);
		}
		
		pipelien.sync();
	}

	@Override
	public void remove(String tableName, String id) {
		String key = this.getStoreKey(tableName, id);
		Jedis client = this.getJedis();
		Pipeline pipeline = client.pipelined();
		//删除行数据
		pipeline.del(getRedisKey(key));
		//在表中删除行
		pipeline.lrem(tableName, 0, id);
		pipeline.sync();
	}
	
	@Override
	public boolean exist(String tableName, String id) {
		Map<String, String> data = get(tableName, id);
		return data != null;
	}

	@Override
	public void buildQueue(String queueKey, List<String> list) {
		buildQueue(queueKey, list, 0);
	}

	@Override
	public void buildQueue(String queueKey, List<String> list, long expireTime) {
		if (CollectionUtil.isNullOrEmpty(list)) {
			throw new IllegalArgumentException("传入列表不允许为空");
		}

		String statusKey = (queueKey + "_status");	//状态 N-未构建,B-构建中,F-已完成
		String statusValue = getKey(statusKey);

		if (!StringUtil.isNullOrEmpty(statusValue) && (statusValue.equalsIgnoreCase(RedisQueueStatus.QUEUE_STATUS_BUILDING) || statusValue.equalsIgnoreCase(RedisQueueStatus.QUEUE_STATUS_FINISH))) {
			//已构建，直接返回
			return;
		} else {
			setKey(statusKey, RedisQueueStatus.QUEUE_STATUS_BUILDING, RedisConstants.SET_KEY_NOT_EXIST, expireTime);

			Jedis client = this.getJedis();
			Pipeline pipeline = client.pipelined();
			String storeKey = getRedisKey(queueKey);
			//清空数据
			pipeline.ltrim(storeKey, -1, 0);
			//插入行数据
			for (String item : list) {
				pipeline.lpush(storeKey, item);
			}
			if (expireTime > 0) {
				pipeline.expire(storeKey, (int) expireTime);
			}
			pipeline.sync();

			setKey(statusKey, RedisQueueStatus.QUEUE_STATUS_FINISH, RedisConstants.SET_KEY_EXIST, expireTime);
		}
	}

	@Override
	public void pushQueue(String queueKey, String value) {
		Jedis client = this.getJedis();
		client.lpush(getRedisKey(queueKey), value);
	}

	@Override
	public String popQueue(String queueKey) {
		Jedis client = this.getJedis();
		String value = client.lpop(getRedisKey(queueKey));
		return value;
	}

	@Override
	public boolean isBuildFinished(String queueKey) {
		String statusKey = (queueKey + "_status");
		if (!StringUtil.isNullOrEmpty(statusKey) && statusKey.equalsIgnoreCase(RedisQueueStatus.QUEUE_STATUS_FINISH)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean existQueue(String queueKey) {
		Jedis client = this.getJedis();
		String statusKey = (queueKey + "_status");
		String key = getRedisKey(statusKey);
		return client.exists(key);
	}

	@Override
	public void waitQueueBuildFinish(String queueKey) {
		int firstTime = 1, maxTime = 10;
		waitQueueBuildFinish(queueKey, firstTime, maxTime);
	}

	private void waitQueueBuildFinish(String queueKey, int times, int maxTimes) {
		String statusKey = (queueKey + "_status");
		String statusValue = getKey(statusKey);
		if (!StringUtil.isNullOrEmpty(statusValue) && statusValue.equalsIgnoreCase(RedisQueueStatus.QUEUE_STATUS_BUILDING)) {
			//等待构建
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (times > maxTimes) {
				LoggerManager.error("等待redis队列构建超时，key：" + queueKey + "，times：" + times);
				return;
			}
			int newTimes = (times + 1);
			waitQueueBuildFinish(queueKey, newTimes, maxTimes);
		}
	}

	@Override
	public boolean isEmptyQueue(String queueKey) {
		Jedis client = this.getJedis();
		long len = client.llen(getRedisKey(queueKey));
		return len <= 0;
	}

	@Override
	public String getQueueStatus(String queueKey) {
		String statusKey = (queueKey + "_status");
		String queueStatus = getKey(statusKey);
		return queueStatus;
	}

	/**
	 * 获取数据存储的key值
	 * @param tableName
	 * @param id
	 * @return
	 */
	private String getStoreKey(String tableName, String id) {
		return tableName + ":" + id;
	}

	private Jedis getJedis() {
		Jedis client = new Jedis(redisIpAddress, Integer.parseInt(redisPort));
		if (!StringUtil.isNullOrEmpty(auth)) {
			client.auth(auth);
		}
		return client;
	}

	private String getRedisKey(String key) {
		return REDIS_KEY_PREFIX + key;
	}

	// getter and setter
	public String getRedisIpAddress() {
		return redisIpAddress;
	}

	public void setRedisIpAddress(String redisIpAddress) {
		this.redisIpAddress = redisIpAddress;
	}

	public String getRedisPort() {
		return redisPort;
	}

	public void setRedisPort(String redisPort) {
		this.redisPort = redisPort;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}
}
