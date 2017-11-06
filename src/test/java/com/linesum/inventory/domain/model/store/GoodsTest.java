package com.linesum.inventory.domain.model.store;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by zhengjx on 2017/11/6.
 */
public class GoodsTest {

    private Goods goods;

    @Before
    public void setUp() throws Exception {
        goods = new Goods(new SkuCode(UUID.randomUUID().toString()), 100, new BigDecimal("100.00"));
    }

    @Test
    public void add() throws Exception {
        goods.add(50);
        Assertions.assertThat(goods.getQty()).isEqualTo(150);
    }

    @Test
    public void reduce() throws Exception {
        goods.reduce(50);
        Assertions.assertThat(goods.getQty()).isEqualTo(50);
    }

    @Test
    public void multiply() throws Exception {
        goods.multiply(new BigDecimal("0.50"));
        Assertions.assertThat(goods.getQty()).isEqualTo(50);
    }

}