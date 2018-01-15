package com.linesum.inventory.infrastructure.persistence.repository;

import com.linesum.inventory.domain.model.order.Contact;
import com.linesum.inventory.domain.model.order.ContactId;
import com.linesum.inventory.domain.model.store.*;
import com.linesum.inventory.BaseJunitTestCase;
import com.linesum.inventory.domain.repository.LogicStoreRepository;
import com.linesum.inventory.domain.repository.PhysicalStoreRepository;
import com.linesum.inventory.infrastructure.persistence.jpa.GoodsRepositoryJpa;
import com.linesum.inventory.infrastructure.persistence.po.GoodsPo;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by zhengjx on 2017/10/27.
 */
public class LogicStoreRepositoryImplTest extends BaseJunitTestCase {

    @Autowired
    private LogicStoreRepository logicStoreRepository;

    @Autowired
    private PhysicalStoreRepository physicalStoreRepository;

    @Autowired
    private GoodsRepositoryJpa goodsRepositoryJpa;

    private GoodsPo goodsPo;

    private PhysicalStore.PhysicalStoreId physicalStoreId;

    @Before
    public void setUp() throws Exception {
        goodsPo = goodsRepositoryJpa.save(new GoodsPo(null, UUID.randomUUID().toString(), new BigDecimal("100.00")));

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
        physicalStoreId = physicalStoreRepository.save(physicalStore);
    }

    @Test
    public void test() throws Exception {
        LogicStore logicStore = new LogicStore(
                new LogicStore.LogicStoreId(null),
                Lists.newArrayList(
                        new Goods(new SkuCode(goodsPo.getSkuCode()), 100, goodsPo.getPrice())
                ),
                new PhysicalStore(physicalStoreId, null, null, null)
        );
        // test save
        LogicStore.LogicStoreId logicStoreId = logicStoreRepository.save(logicStore);

        // test find
        LogicStore logicStore1 = logicStoreRepository.find(logicStoreId);
        Assertions.assertThat(logicStore1)
                .isNotNull()
                .hasNoNullFieldsOrPropertiesExcept();

        Assertions.assertThat(logicStore1.getPhysicalStore().getPhysicalStoreId().getId())
                .isNotNull();

        Assertions.assertThat(logicStore1.getGoodsList())
                .isNotNull()
                .isNotEmpty()
                .extracting("qty", "price")
                .contains(Assertions.tuple(100, new BigDecimal("100.00")));

        Assertions.assertThat(logicStore1.getGoodsList().get(0).getSkuCode().getCode())
                .isEqualTo(goodsPo.getSkuCode());
    }

}