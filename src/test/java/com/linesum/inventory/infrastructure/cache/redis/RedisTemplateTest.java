package com.linesum.inventory.infrastructure.cache.redis;

import com.linesum.inventory.domain.model.store.Goods;
import com.linesum.inventory.domain.model.store.SkuCode;
import com.linesum.inventory.infrastructure.persistence.BaseJunitTestCase;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by zhengjx on 2017/11/1.
 */
public class RedisTemplateTest extends BaseJunitTestCase {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test() throws Exception {
        redisTemplate.opsForValue().set("sku_code_1", new Goods(
                new SkuCode("sku_code_1"),
                100,
                new BigDecimal("100.00")));

        Object value = redisTemplate.opsForValue().get("sku_code_1");
        Assertions.assertThat(value).isInstanceOf(SkuCode.class);

        Goods goods = (Goods) value;
        Assertions.assertThat(goods)
                .hasNoNullFieldsOrProperties();
    }

}