package com.linesum.inventory.infrastructure.cache.redis;

import com.alibaba.fastjson.JSONObject;
import com.linesum.inventory.BaseJunitTestCase;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;

/**
 * Created by zhengjx on 2017/11/1.
 */
public class RedisTemplateTest extends BaseJunitTestCase {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test() throws Exception {
        RedisValueObject valueObject = new RedisValueObject(10000L, "redis_name");
        String key = valueObject.getId().toString();
        redisTemplate.opsForValue().set(key, JSONObject.toJSONString(valueObject));

        Object value = redisTemplate.opsForValue().get(key);

        RedisValueObject redisValueObject = JSONObject.parseObject(value.toString(), RedisValueObject.class);

        Assertions.assertThat(redisValueObject)
                .hasNoNullFieldsOrProperties();
    }

    public class RedisValueObject implements Serializable {
        private Long id;
        private String name;

        public RedisValueObject(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}