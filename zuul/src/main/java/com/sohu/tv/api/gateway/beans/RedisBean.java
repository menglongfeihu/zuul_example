/**
 * Copyright (c) 2012 Sohu. All Rights Reserved
 */
package com.sohu.tv.api.gateway.beans;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisBean {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void Set(String key, String value, long timeout) {
        redisTemplate.opsForValue().set(key,value, timeout, TimeUnit.SECONDS);
    }


}
