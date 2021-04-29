package com.gyg.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author 郭永钢
 */
@Service
public class RedisServiceImpl {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public void insertToken(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value, 60 * 5*6, TimeUnit.SECONDS);
    }
}
