package com.lmtech.redis.service;

import com.lmtech.common.PageData;
import com.lmtech.model.IdEntity;

import java.util.List;
import java.util.Map;

/**
 * Redis数据存储服务
 *
 * @author huang.jb
 */
public interface RedisDataService {
    /**
     * 获取key值
     *
     * @param key
     * @return
     */
    String getKey(String key);

    /**
     * 获取key值对应的对象
     *
     * @param key
     * @param type
     * @param <T>
     * @return
     */
    <T> T getKeyObject(String key, Class<?> type);

    /**
     * 获取key值对应的对象
     *
     * @param key
     * @param type
     * @param removeFailed
     * @param <T>
     * @return
     */
    <T> T getKeyObject(String key, Class<?> type, boolean removeFailed);

    /**
     * 获取key列表值
     *
     * @param keys
     * @return
     */
    List<String> getKeys(List<String> keys);

    /**
     * 设置key值
     *
     * @param key
     * @param value
     * @return
     */
    boolean setKey(String key, String value);

    /**
     * 设置key对象
     *
     * @param key
     * @param object
     * @return
     */
    boolean setKeyObject(String key, Object object);

    /**
     * 设置key值，包含过期时间
     *
     * @param key
     * @param value
     * @param expireTime seconds
     * @return
     */
    boolean setKey(String key, String value, long expireTime);

    /**
     * 设置key值，包含过期时间
     *
     * @param key
     * @param value
     * @param nxxx       :  NX|XX, NX -- Only set the key if it does not already exist. XX -- Only set the key
     *                   if it already exist.
     * @param expireTime seconds
     * @return
     */
    @Deprecated
    boolean setKey(String key, String value, String nxxx, long expireTime);

    /**
     * 设置key对象值，包含过期时间
     *
     * @param key
     * @param object
     * @param nxxx
     * @param expireTime
     * @return
     */
    boolean setKeyObject(String key, Object object, String nxxx, long expireTime);

    /**
     * 删除key值
     *
     * @param key
     */
    void removeKey(String key);

    /**
     * 删除key值列表
     *
     * @param keys
     */
    void removeKeys(List<String> keys);

    /**
     * 设置过期时间
     *
     * @param key
     * @param seconds
     */
    void setExpireTime(String key, int seconds);

    /**
     * 获取数据
     *
     * @param tableName
     * @param id
     * @return
     */
    Map<String, String> get(String tableName, String id);

    /**
     * 获取所有数据
     *
     * @param tableName
     * @return
     */
    <T> List<T> getAll(String tableName, Class<T> clazz);

    /**
     * 获取分页数据
     *
     * @param tableName
     * @param pageIndex
     * @param pageSize
     * @return
     */
    PageData getPageData(String tableName, int pageIndex, int pageSize);

    /**
     * 添加或更新数据
     *
     * @param tableName
     * @param entity    缓存数据
     */
    <T extends IdEntity> void save(String tableName, T entity);

    /**
     * 添加列表数据
     * @param tableName
     * @param entityList
     */
    <T extends IdEntity> void saveAll(String tableName, List<T> entityList);

    /**
     * 删除数据
     *
     * @param tableName
     * @param id
     */
    void remove(String tableName, String id);

    /**
     * 是否存在数据
     *
     * @param tableName
     * @param id
     * @return
     */
    boolean exist(String tableName, String id);

    /**
     * 构建队列
     *
     * @param queueKey
     */
    void buildQueue(String queueKey, List<String> list);

    /**
     * 构建队列
     *
     * @param queueKey
     * @param list
     * @param expireTime
     */
    void buildQueue(String queueKey, List<String> list, long expireTime);

    /**
     * 添加数据
     *
     * @param queueKey
     * @param value
     */
    void pushQueue(String queueKey, String value);

    /**
     * 取出数据
     *
     * @param queueKey
     * @return
     */
    String popQueue(String queueKey);

    /**
     * 是否构建完毕
     *
     * @param queueKey
     * @return
     */
    boolean isBuildFinished(String queueKey);

    /**
     * 是否存在队列
     *
     * @param queueKey
     * @return
     */
    boolean existQueue(String queueKey);

    /**
     * 等待队列构建完毕，如已构建成功则跳出，否则定时等待
     * @param queueKey
     */
    void waitQueueBuildFinish(String queueKey);

    /**
     * 是否空队列
     *
     * @param queueKey
     * @return
     */
    boolean isEmptyQueue(String queueKey);

    /**
     * 获取队列状态
     * @param queueKey
     * @return
     */
    String getQueueStatus(String queueKey);
}
