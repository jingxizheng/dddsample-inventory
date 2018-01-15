package com.linesum.inventory.infrastructure.cache.redis;

import com.alibaba.fastjson.JSONObject;
import com.linesum.inventory.domain.model.store.SalesStore;
import com.linesum.inventory.domain.repository.SalesStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

/**
 * Created by zhengjx on 2017/10/31.
 */
@Repository
public class SalesStoreRepositoryRedisImpl implements SalesStoreRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public SalesStore find(SalesStore.SalesStoreId salesStoreId) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object o = valueOperations.get(salesStoreId.idString());
        JSONObject jsonObject = (JSONObject) o;
        return JSONObject.parseObject(jsonObject.toJSONString(), SalesStore.class);
    }

    @Override
    public SalesStore.SalesStoreId save(SalesStore salesStore) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(salesStore.getSalesStoreId().idString(), salesStore);
        return salesStore.getSalesStoreId();
    }
}
