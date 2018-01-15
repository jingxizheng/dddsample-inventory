package com.linesum.inventory.infrastructure.persistence.repository;

import com.linesum.inventory.domain.model.order.Contact;
import com.linesum.inventory.domain.model.order.ContactId;
import com.linesum.inventory.domain.model.store.*;
import com.linesum.inventory.BaseJunitTestCase;
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
 * Created by zhengjx on 2017/10/26.
 */
public class PhysicalStoreRepositoryImplTest extends BaseJunitTestCase {

    @Autowired
    private GoodsRepositoryJpa goodsRepositoryJpa;

    @Autowired
    private PhysicalStoreRepository physicalStoreRepository;

    private GoodsPo goodsPo;

    @Before
    public void setUp() throws Exception {
        goodsPo = goodsRepositoryJpa.save(new GoodsPo(null, UUID.randomUUID().toString(), new BigDecimal("100.00")));
    }

    @Test
    public void test() throws Exception {
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
        // test save
        PhysicalStore.PhysicalStoreId physicalStoreId = physicalStoreRepository.save(physicalStore);

        // test find
        PhysicalStore physicalStore1 = physicalStoreRepository.find(physicalStoreId);
        Assertions.assertThat(physicalStore1)
                .isNotNull()
                .hasNoNullFieldsOrPropertiesExcept();
        Assertions.assertThat(physicalStore1.getWarehouseInfo())
                .isNotNull()
                .hasFieldOrPropertyWithValue("usedCapacity", 100)
                .hasFieldOrPropertyWithValue("totalCapacity", 1000);
        Assertions.assertThat(physicalStore1.getWarehouseInfo().getContact())
                .isNotNull()
                .hasFieldOrPropertyWithValue("name", "contact_name")
                .hasFieldOrPropertyWithValue("address", "contact_address")
                .hasFieldOrPropertyWithValue("telephone", "15159866655");
        Assertions.assertThat(physicalStore1.getGoodsList())
                .isNotNull()
                .isNotEmpty()
                .extracting("qty", "price")
                .contains(Assertions.tuple(100, new BigDecimal("100.00")));
        Assertions.assertThat(physicalStore1.getGoodsList().get(0).getSkuCode().getCode())
                .isEqualTo(goodsPo.getSkuCode());
    }


}