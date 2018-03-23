package com.lmtech.redis.service;

import com.lmtech.redis.exception.RedisInvokeException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * Redis方法体执行接口
 *
 * @param <R>
 */
public abstract class RedisInvoker<R> {

    private RedisConnectionFactory redisConnectionFactory;

    public RedisInvoker(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    public R execute() {
        RedisConnection connection = null;
        try {
            connection = redisConnectionFactory.getConnection();
            R r = invoke(connection);
            return r;
        } catch (Exception e) {
            throw new RedisInvokeException();
        } finally {
            if (connection != null) {
                try {
                    if (!connection.isClosed()) {
                        connection.close();
                    }
                } finally {
                }
            }
        }
    }

    /**
     * redis执行方法
     *
     * @return
     */
    public abstract R invoke(RedisConnection connection);
}
