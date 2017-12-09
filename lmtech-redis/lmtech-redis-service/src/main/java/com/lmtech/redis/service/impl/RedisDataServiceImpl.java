package com.lmtech.redis.service.impl;

import com.lmtech.common.PageData;
import com.lmtech.redis.constants.RedisConstants;
import com.lmtech.redis.constants.RedisQueueStatus;
import com.lmtech.redis.exception.RedisJsonParseException;
import com.lmtech.redis.service.RedisDataService;
import com.lmtech.redis.service.RedisInvoker;
import com.lmtech.util.CollectionUtil;
import com.lmtech.util.JsonUtil;
import com.lmtech.util.LoggerManager;
import com.lmtech.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RedisDataServiceImpl implements RedisDataService {

	private String REDIS_KEY_PREFIX = "ea_card_crm:";

	@Autowired
	private RedisConnectionFactory redisConnectionFactory;

	@Override
	public String getKey(String key) {
		if (StringUtil.isNullOrEmpty(key)) {
			throw new IllegalArgumentException("传入key不允许为空");
		}

		RedisInvoker<String> redisInvoker = new RedisInvoker<String>(redisConnectionFactory) {
			@Override
			public String invoke(RedisConnection connection) {
				byte[] result = connection.get(getRedisKey(key).getBytes());
				if (result != null) {
					return new String(result);
				} else {
					return null;
				}
			}
		};
		return redisInvoker.execute();
	}

	@Override
	public <T> T getKeyObject(String key, Class<?> type) {
		if (StringUtil.isNullOrEmpty(key)) {
			throw new IllegalArgumentException("传入key不允许为空");
		}
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
		if (CollectionUtil.isNullOrEmpty(keys)) {
			throw new IllegalArgumentException("传入key列表不允许为空");
		}

		RedisInvoker<List<String>> redisInvoker = new RedisInvoker<List<String>>(redisConnectionFactory) {
			@Override
			public List<String> invoke(RedisConnection connection) {
				List<String> result = new ArrayList<String>();
				connection.openPipeline();
				for (String key : keys) {
					connection.get(getRedisKey(key).getBytes());
				}
				List<Object> values = connection.closePipeline();
				for (Object value : values) {
					result.add(String.valueOf(value));
				}
				return result;
			}
		};
		return redisInvoker.execute();
	}

	@Override
	public boolean setKey(String key, String value) {
		if (StringUtil.isNullOrEmpty(key)) {
			throw new IllegalArgumentException("传入key不允许为空");
		}

		RedisInvoker<Boolean> redisInvoker = new RedisInvoker<Boolean>(redisConnectionFactory) {
			@Override
			public Boolean invoke(RedisConnection connection) {
				connection.set(getRedisKey(key).getBytes(), value.getBytes());
				return true;
			}
		};
		return redisInvoker.execute();
	}

	@Override
	public boolean setKeyObject(String key, Object object) {
		String json = JsonUtil.toJson(object);
		return this.setKey(getRedisKey(key), json);
	}

	@Override
	public boolean setKey(String key, String value, long expireTime) {
		return setKey(key, value, null, expireTime);
	}

	@Override
	@Deprecated
	public boolean setKey(String key, String value, String nxxx, long expireTime) {
		if (StringUtil.isNullOrEmpty(key)) {
			throw new IllegalArgumentException("传入key不允许为空");
		}
		if (expireTime <= 0) {
			throw new IllegalArgumentException("redis expire time must > 0");
		}

		RedisInvoker<Boolean> redisInvoker = new RedisInvoker<Boolean>(redisConnectionFactory) {
			@Override
			public Boolean invoke(RedisConnection connection) {
				connection.setEx(getRedisKey(key).getBytes(), expireTime, value.getBytes());
				return true;
			}
		};
		return redisInvoker.execute();
	}

	@Override
	public boolean setKeyObject(String key, Object object, String nxxx, long expireTime) {
		if (StringUtil.isNullOrEmpty(key)) {
			throw new IllegalArgumentException("传入key不允许为空");
		}
		if (expireTime <= 0) {
			throw new IllegalArgumentException("redis expire time must > 0");
		}

		String json = JsonUtil.toJson(object);
		return this.setKey(getRedisKey(key), json, nxxx, expireTime);
	}

	@Override
	public void removeKey(String key) {
		if (StringUtil.isNullOrEmpty(key)) {
			throw new IllegalArgumentException("传入key不允许为空");
		}

		RedisInvoker<Boolean> redisInvoker = new RedisInvoker<Boolean>(redisConnectionFactory) {
			@Override
			public Boolean invoke(RedisConnection connection) {
				connection.del(getRedisKey(key).getBytes());
				return true;
			}
		};
		redisInvoker.execute();
	}

	@Override
	public void removeKeys(List<String> keys) {
		if (CollectionUtil.isNullOrEmpty(keys)) {
			throw new IllegalArgumentException("传入key列表不允许为空");
		}

		RedisInvoker<Boolean> redisInvoker = new RedisInvoker<Boolean>(redisConnectionFactory) {
			@Override
			public Boolean invoke(RedisConnection connection) {
				connection.openPipeline();
				for (String key : keys) {
					connection.del(getRedisKey(key).getBytes());
				}
				connection.closePipeline();
				return true;
			}
		};
		redisInvoker.execute();
	}

	@Override
	public void setExpireTime(String key, int seconds) {
		RedisInvoker<Boolean> redisInvoker = new RedisInvoker<Boolean>(redisConnectionFactory) {
			@Override
			public Boolean invoke(RedisConnection connection) {
				connection.expire(getRedisKey(key).getBytes(), seconds);
				return true;
			}
		};
		redisInvoker.execute();
	}

	@Override
	public Map<String, String> get(String tableName, String id) {
		if (StringUtil.isNullOrEmpty(tableName)) {
			throw new IllegalArgumentException("传入tableName不允许为空");
		}
		if (StringUtil.isNullOrEmpty(id)) {
			throw new IllegalArgumentException("传入id不允许为空");
		}

		RedisInvoker<Map<String, String>> redisInvoker = new RedisInvoker<Map<String, String>>(redisConnectionFactory) {
			@Override
			public Map<String, String> invoke(RedisConnection connection) {
				Map<String, String> strResult = new HashMap<>();
				Map<byte[], byte[]> result = connection.hGetAll(getRedisKey(tableName).getBytes());
				if (result != null && result.size() > 0) {
					for (byte[] key : result.keySet()) {
						String keyStr = new String(key);
						String valueStr = new String(result.get(key));
						strResult.put(keyStr, valueStr);
					}
				}
				return strResult;
			}
		};
		return redisInvoker.execute();
	}

	@Override
	public <T> List<T> getAll(String tableName, Class<T> clazz) {
		if (StringUtil.isNullOrEmpty(tableName)) {
			throw new IllegalArgumentException("传入tableName不允许为空");
		}

		RedisInvoker<List<T>> redisInvoker = new RedisInvoker<List<T>>(redisConnectionFactory) {
			@Override
			public List<T> invoke(RedisConnection connection) {
				List<T> ts = new ArrayList<>();
				byte[] tbName = tableName.getBytes();
				if (connection.exists(tbName)) {
					Map<byte[], byte[]> byteDatas = connection.hGetAll(tbName);
					Map<String, String> datas = CollectionUtil.convertByteToStringMap(byteDatas);
					for (String value : datas.values()) {
						T obj = (T) JsonUtil.fromJson(value, clazz);
						ts.add(obj);
					}

					return ts;
				} else {
					return new ArrayList<>();
				}
			}
		};
		return redisInvoker.execute();
	}

	@Override
	public PageData getPageData(String tableName, int pageIndex, int pageSize) {
		return null;
	}

	@Override
	public void addOrUpdate(String tableName, String id, Object entity) {
		if (StringUtil.isNullOrEmpty(tableName)) {
			throw new IllegalArgumentException("传入tableName不允许为空");
		}
		if (StringUtil.isNullOrEmpty(id)) {
			throw new IllegalArgumentException("传入id不允许为空");
		}

		RedisInvoker<Boolean> redisInvoker = new RedisInvoker<Boolean>(redisConnectionFactory) {
			@Override
			public Boolean invoke(RedisConnection connection) {
				boolean isAdd = false;
				if (!connection.exists(getRedisKey(tableName).getBytes())) {
					isAdd = true;
				}
				connection.openPipeline();
				//写入行数据
				connection.hSet(getRedisKey(tableName).getBytes(), id.getBytes(), JsonUtil.toJson(entity).getBytes());
				if (isAdd) {
					//如果添加行，将表的行id写入列表中
					connection.lPush(getRedisKey(tableName + "_page").getBytes(), id.getBytes());
				}
				connection.closePipeline();
				return true;
			}
		};
		redisInvoker.execute();
	}

	@Override
	public void remove(String tableName, String id) {
		if (StringUtil.isNullOrEmpty(tableName)) {
			throw new IllegalArgumentException("传入tableName不允许为空");
		}
		if (StringUtil.isNullOrEmpty(id)) {
			throw new IllegalArgumentException("传入id不允许为空");
		}

		RedisInvoker<Boolean> redisInvoker = new RedisInvoker<Boolean>(redisConnectionFactory) {
			@Override
			public Boolean invoke(RedisConnection connection) {
				connection.openPipeline();
				//删除行数据
				connection.hDel(getRedisKey(tableName).getBytes(), id.getBytes());
				//在表中删除行
				connection.lRem(tableName.getBytes(), 0, id.getBytes());
				return true;
			}
		};
		redisInvoker.execute();
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

			RedisInvoker<Boolean> redisInvoker = new RedisInvoker<Boolean>(redisConnectionFactory) {
				@Override
				public Boolean invoke(RedisConnection connection) {
					connection.openPipeline();
					String storeKey = getRedisKey(queueKey);
					//清空数据
					connection.lTrim(storeKey.getBytes(), -1, 0);
					//插入行数据
					for (String item : list) {
						connection.lPush(storeKey.getBytes(), item.getBytes());
					}
					if (expireTime > 0) {
						connection.expire(storeKey.getBytes(), (int) expireTime);
					}
					connection.closePipeline();

					setKey(statusKey, RedisQueueStatus.QUEUE_STATUS_FINISH, RedisConstants.SET_KEY_EXIST, expireTime);

					return true;
				}
			};
			redisInvoker.execute();
		}
	}

	@Override
	public void pushQueue(String queueKey, String value) {
		if (StringUtil.isNullOrEmpty(queueKey)) {
			throw new IllegalArgumentException("传入queueKey不允许为空");
		}
		if (StringUtil.isNullOrEmpty(value)) {
			throw new IllegalArgumentException("传入value不允许为空");
		}

		RedisInvoker<Boolean> redisInvoker = new RedisInvoker<Boolean>(redisConnectionFactory) {
			@Override
			public Boolean invoke(RedisConnection connection) {
				connection.lPush(getRedisKey(queueKey).getBytes(), value.getBytes());
				return true;
			}
		};
		redisInvoker.execute();
	}

	@Override
	public String popQueue(String queueKey) {
		if (StringUtil.isNullOrEmpty(queueKey)) {
			throw new IllegalArgumentException("传入queueKey不允许为空");
		}

		RedisInvoker<String> redisInvoker = new RedisInvoker<String>(redisConnectionFactory) {
			@Override
			public String invoke(RedisConnection connection) {
				byte[] value = connection.lPop(getRedisKey(queueKey).getBytes());
				return new String(value);
			}
		};
		return redisInvoker.execute();
	}

	@Override
	public boolean isBuildFinished(String queueKey) {
		if (StringUtil.isNullOrEmpty(queueKey)) {
			throw new IllegalArgumentException("传入queueKey不允许为空");
		}

		String statusKey = (queueKey + "_status");
		if (!StringUtil.isNullOrEmpty(statusKey) && statusKey.equalsIgnoreCase(RedisQueueStatus.QUEUE_STATUS_FINISH)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean existQueue(String queueKey) {
		if (StringUtil.isNullOrEmpty(queueKey)) {
			throw new IllegalArgumentException("传入queueKey不允许为空");
		}

		String statusKey = (queueKey + "_status");
		String key = getRedisKey(statusKey);
		RedisInvoker<Boolean> redisInvoker = new RedisInvoker<Boolean>(redisConnectionFactory) {
			@Override
			public Boolean invoke(RedisConnection connection) {
				return connection.exists(key.getBytes());
			}
		};
		return redisInvoker.execute();
	}

	@Override
	public void waitQueueBuildFinish(String queueKey) {
		int firstTime = 1, maxTime = 10;
		waitQueueBuildFinish(queueKey, firstTime, maxTime);
	}

	private void waitQueueBuildFinish(String queueKey, int times, int maxTimes) {
		if (StringUtil.isNullOrEmpty(queueKey)) {
			throw new IllegalArgumentException("传入queueKey不允许为空");
		}

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
		if (StringUtil.isNullOrEmpty(queueKey)) {
			throw new IllegalArgumentException("传入queueKey不允许为空");
		}

		RedisInvoker<Boolean> redisInvoker = new RedisInvoker<Boolean>(redisConnectionFactory) {
			@Override
			public Boolean invoke(RedisConnection connection) {
				long len = connection.lLen(getRedisKey(queueKey).getBytes());
				return len <= 0;
			}
		};
		return redisInvoker.execute();
	}

	@Override
	public String getQueueStatus(String queueKey) {
		String statusKey = (queueKey + "_status");
		String queueStatus = getKey(statusKey);
		return queueStatus;
	}

	private String getRedisKey(String key) {
		return REDIS_KEY_PREFIX + key;
	}
}
