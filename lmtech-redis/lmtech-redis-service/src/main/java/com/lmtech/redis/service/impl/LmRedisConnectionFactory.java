package com.lmtech.redis.service.impl;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jredis.JredisConnectionFactory;
import org.springframework.stereotype.Service;

@Service
public class LmRedisConnectionFactory extends JredisConnectionFactory implements RedisConnectionFactory {
}
