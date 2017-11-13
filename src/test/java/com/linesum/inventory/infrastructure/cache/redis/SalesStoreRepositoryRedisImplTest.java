package com.linesum.inventory.infrastructure.cache.redis;

import com.google.common.collect.Lists;
import com.linesum.inventory.BaseJunitTestCase;
import com.linesum.inventory.domain.model.store.*;
import com.linesum.inventory.domain.model.storeconfig.StoreConfig;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by zhengjx on 2017/11/7.
 */
public class SalesStoreRepositoryRedisImplTest extends BaseJunitTestCase {

    @Autowired
    private SalesStoreRepositoryRedisImpl salesStoreRepositoryRedis;

    private String skuCode = UUID.randomUUID().toString();

    @Test
    public void test() throws Exception {
        SalesStore salesStore = new SalesStore(
                new SalesStore.SalesStoreId(1L),
                Lists.newArrayList(
                        new LogicStore(new LogicStore.LogicStoreId(2L),
                                Lists.newArrayList(new Goods(new SkuCode(skuCode), 100, new BigDecimal("100.00"))),
                                new PhysicalStore(null, null, null, null)
                        )
                ),
                new Channel(new Channel.ChannelId(3L), "channel_name", Channel.ChannelType.SUPPLIER, null),
                Lists.newArrayList(
                        new StoreConfig.SalesRatioConfig(new BigDecimal("0.50"))
                )
        );

        SalesStore.SalesStoreId salesStoreId = salesStoreRepositoryRedis.save(salesStore);

        Assertions.assertThat(salesStoreId)
                .isNotNull()
                .extracting(SalesStore.SalesStoreId::getId)
                .contains(1L);

        SalesStore salesStoreHit = salesStoreRepositoryRedis.find(new SalesStore.SalesStoreId(1L));

        Assertions.assertThat(salesStoreHit)
                .isNotNull();

        Assertions.assertThat(salesStoreHit.getGoodsList())
                .extracting(goods -> goods.getSkuCode().getCode(), Goods::getQty, Goods::getPrice)
                .contains(Assertions.tuple(skuCode, 50, new BigDecimal("100.00")));

        Assertions.assertThat(salesStoreHit.getLogicStoreList())
                .isNotNull()
                .isNotEmpty()
                .first()
                .isNotNull();

        Assertions.assertThat(salesStoreHit.getLogicStoreList().get(0).getGoodsList())
                .extracting(goods -> goods.getSkuCode().getCode(), Goods::getQty, Goods::getPrice)
                .contains(Assertions.tuple(skuCode, 100, new BigDecimal("100.00")));

    }

}