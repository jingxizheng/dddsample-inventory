package com.linesum.inventory.domain.model.store;

import com.linesum.inventory.domain.model.order.Contact;
import com.linesum.inventory.domain.model.order.ContactId;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zhengjx on 2017/10/27.
 */
public class PhysicalStoreTest {

    private PhysicalStore physicalStore;

    private List<Goods> pendingGoodsList;

    private SkuCode skuCode1 = new SkuCode("sku_code_1");

    private SkuCode skuCode2 = new SkuCode("sku_code_2");

    @Before
    public void setUp() throws Exception {
        physicalStore = new PhysicalStore(
                new PhysicalStore.PhysicalStoreId(1L),
                new WarehouseId(1L),
                new WarehouseInfo(
                        new Contact(new ContactId(1L), "name", "address", "12345678901"),
                        200,
                        1000
                ),
                Lists.newArrayList(
                        new Goods(skuCode1, 100, new BigDecimal("100.00")),
                        new Goods(skuCode2, 100, new BigDecimal("200.00"))
                )
        );

        pendingGoodsList = Lists.newArrayList(
                new Goods(skuCode1, 50, new BigDecimal("100.00")),
                new Goods(skuCode2, 50, new BigDecimal("200.00"))
        );

    }

    @Test
    public void inStore() throws Exception {
        physicalStore.inStore(pendingGoodsList);

        Assertions.assertThat(physicalStore.getGoodsList())
                .extracting("skuCode", "qty", "price")
                .contains(
                        Assertions.tuple(skuCode1, 150, new BigDecimal("100.00")),
                        Assertions.tuple(skuCode2, 150, new BigDecimal("200.00"))
                );

        Assertions.assertThat(physicalStore.getWarehouseInfo().getUsedCapacity())
                .isEqualTo(300);
    }

    @Test
    public void outStore() throws Exception {
        physicalStore.outStore(pendingGoodsList);

        Assertions.assertThat(physicalStore.getGoodsList())
                .extracting("skuCode", "qty", "price")
                .contains(
                        Assertions.tuple(skuCode1, 50, new BigDecimal("100.00")),
                        Assertions.tuple(skuCode2, 50, new BigDecimal("200.00"))
                );

        Assertions.assertThat(physicalStore.getWarehouseInfo().getUsedCapacity())
                .isEqualTo(100);
    }

}