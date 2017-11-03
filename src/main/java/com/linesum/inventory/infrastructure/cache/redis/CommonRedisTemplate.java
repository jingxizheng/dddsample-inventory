package com.linesum.inventory.infrastructure.cache.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by zhengjx on 2017/10/31.
 */
public class CommonRedisTemplate {

    @Autowired
    private RedisTemplate redisTemplate;


}
