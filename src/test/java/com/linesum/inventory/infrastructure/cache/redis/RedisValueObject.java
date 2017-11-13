package com.linesum.inventory.infrastructure.cache.redis;

/**
 * Created by zhengjx on 2017/11/7.
 */
public class RedisValueObject {
    private Long id;
    private String name;

    public RedisValueObject(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public RedisValueObject() {
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
