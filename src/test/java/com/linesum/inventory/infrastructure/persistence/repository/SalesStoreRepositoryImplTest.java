package com.linesum.inventory.infrastructure.persistence.repository;

import com.linesum.inventory.domain.model.order.Contact;
import com.linesum.inventory.domain.model.order.ContactId;
import com.linesum.inventory.domain.model.store.*;
import com.linesum.inventory.BaseJunitTestCase;
import com.linesum.inventory.infrastructure.persistence.jpa.*;
import com.linesum.inventory.infrastructure.persistence.jpa.po.*;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by zhengjx on 2017/11/6.
 */
public class SalesStoreRepositoryImplTest extends BaseJunitTestCase {

    @Autowired
    private SalesStoreRepositoryImpl salesStoreRepository;

    @Autowired
    private GoodsRepositoryJpa goodsRepositoryJpa;

    @Autowired
    private PhysicalStoreRepository physicalStoreRepository;

    @Autowired
    private LogicStoreRepository logicStoreRepository;

    @Autowired
    private ChannelRepositoryJpa channelRepositoryJpa;

    @Autowired
    private SalesStoreLogicStoreMiddleRepositoryJpa salesStoreLogicStoreMiddleRepositoryJpa;

    @Autowired
    private SalesStoreRepositoryJpa salesStoreRepositoryJpa;

    @Autowired
    private SalesRatioConfigRepositoryJpa salesRatioConfigRepositoryJpa;

    private Long salesStoreId;

    @Before
    public void setUp() throws Exception {
        // save goods
        GoodsPo goodsPo = goodsRepositoryJpa.save(new GoodsPo(null, UUID.randomUUID().toString(), new BigDecimal("100.00")));

        PhysicalStore physicalStore = new PhysicalStore(
                new PhysicalStore.PhysicalStoreId(null),
                new WarehouseId(null),
                new WarehouseInfo(
                        new Contact(new ContactId(null), "contact_name", "contact_address", "15159866655"),
                        100, 1000
                ),
                Lists.newArrayList(
                        new Goods(new SkuCode(goodsPo.getSkuCode()), 100, goodsPo.getPrice())
                )
        );

        // save physical store
        PhysicalStore.PhysicalStoreId physicalStoreId = physicalStoreRepository.save(physicalStore);

        LogicStore logicStore = new LogicStore(
                new LogicStore.LogicStoreId(null),
                Lists.newArrayList(
                        new Goods(new SkuCode(goodsPo.getSkuCode()), 100, goodsPo.getPrice())
                ),
                new PhysicalStore(physicalStoreId, null, null, null)
        );

        // save logic store
        LogicStore.LogicStoreId logicStoreId = logicStoreRepository.save(logicStore);

        // save channel
        ChannelPo channelPo = channelRepositoryJpa.save(new ChannelPo(null, "channel_name", Channel.ChannelType.SUPPLIER, null));

        // save sales store
        SalesStorePo salesStorePo = salesStoreRepositoryJpa.save(new SalesStorePo(null, channelPo.getId()));
        salesStoreId = salesStorePo.getId();

        // save sales store logic store middle
        SalesStoreLogicStoreMiddlePo salesStoreLogicStoreMiddlePo = salesStoreLogicStoreMiddleRepositoryJpa.save(new SalesStoreLogicStoreMiddlePo(null, salesStoreId, logicStoreId.getId()));

        // save sales store ratio config
        salesRatioConfigRepositoryJpa.save(new SalesRatioConfigPo(null, salesStoreId, new BigDecimal("0.50")));
    }

    @Test
    public void find() throws Exception {
        SalesStore salesStore = salesStoreRepository.find(new SalesStore.SalesStoreId(salesStoreId));

        Assertions.assertThat(salesStore)
                .isNotNull()
                .hasNoNullFieldsOrProperties();

        Assertions.assertThat(salesStore.getChannel())
                .isNotNull()
                .hasNoNullFieldsOrProperties();

        Assertions.assertThat(salesStore.getGoodsList())
                .isNotNull()
                .isNotEmpty()
                .first()
                .hasFieldOrPropertyWithValue("qty", 50);

    }

}