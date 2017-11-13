package com.linesum.inventory.infrastructure.cache.redis;

import com.alibaba.fastjson.JSONObject;
import com.linesum.inventory.BaseJunitTestCase;
import com.linesum.inventory.domain.model.storeconfig.StoreConfig;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;

/**
 * Created by zhengjx on 2017/11/1.
 * 测试redis
 */
public class RedisTemplateTest extends BaseJunitTestCase {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void test() throws Exception {
        RedisValueObject valueObject = new RedisValueObject(10000L, "redis_name");
        String key = valueObject.getId().toString();
        redisTemplate.opsForValue().set(key, valueObject);

        Object value = redisTemplate.opsForValue().get(key);
        RedisValueObject redisValueObject = ((JSONObject) value).toJavaObject(RedisValueObject.class);

        Assertions.assertThat(redisValueObject)
                .hasNoNullFieldsOrProperties();
    }

}