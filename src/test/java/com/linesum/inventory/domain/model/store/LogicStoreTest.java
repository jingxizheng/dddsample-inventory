package com.linesum.inventory.domain.model.store;

import com.linesum.inventory.domain.model.order.Contact;
import com.linesum.inventory.domain.model.order.ContactId;
import com.linesum.inventory.domain.model.order.Order;
import com.linesum.inventory.domain.model.order.OrderId;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zhengjx on 2017/10/27.
 */
public class LogicStoreTest {

    private PhysicalStore physicalStore;

    private LogicStore logicStore;

    private List<Goods> pendingGoodsList;

    private SkuCode skuCode1 = new SkuCode("sku_code_1");

    private SkuCode skuCode2 = new SkuCode("sku_code_2");

    private OrderId orderId = new OrderId(1L);

    private Contact contact = new Contact(new ContactId(1L), "name", "address", "12345678901");

    private Contact sender = new Contact(new ContactId(1L), "sender_name", "sender_address", "12345678901");

    private Contact acceptor = new Contact(new ContactId(1L), "acceptor_name", "acceptor_address", "12345678901");

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

        logicStore = new LogicStore(
                new LogicStore.LogicStoreId(1L),
                Lists.newArrayList(
                        new Goods(skuCode1, 70, new BigDecimal("100.00")),
                        new Goods(skuCode2, 70, new BigDecimal("200.00"))
                ),
                physicalStore
        );
    }

    @Test
    public void inStore() throws Exception {
        Order order = logicStore.inStore(pendingGoodsList, orderId, sender);

        Assertions.assertThat(logicStore.getGoodsList())
                .extracting("skuCode", "qty", "price")
                .contains(
                        Assertions.tuple(skuCode1, 120, new BigDecimal("100.00")),
                        Assertions.tuple(skuCode2, 120, new BigDecimal("200.00"))
                );

        Assertions.assertThat(logicStore.getPhysicalStore().getGoodsList())
                .extracting("skuCode", "qty", "price")
                .contains(
                        Assertions.tuple(skuCode1, 150, new BigDecimal("100.00")),
                        Assertions.tuple(skuCode2, 150, new BigDecimal("200.00"))
                );

        Assertions.assertThat(logicStore.getPhysicalStore().getWarehouseInfo().getUsedCapacity())
                .isEqualTo(300);

        Assertions.assertThat(order.getSendDate())
                .isNotNull();
        Assertions.assertThat(order.getSender())
                .isNotNull()
                .hasNoNullFieldsOrProperties();
        Assertions.assertThat(order.getAcceptor())
                .isNotNull()
                .hasNoNullFieldsOrProperties();
        Assertions.assertThat(order.getOrderGoodsList())
                .isNotNull()
                .isNotEmpty();
    }

    @Test
    public void outStore() throws Exception {
        Order order = logicStore.outStore(pendingGoodsList, orderId, acceptor);

        Assertions.assertThat(logicStore.getGoodsList())
                .extracting("skuCode", "qty", "price")
                .contains(
                        Assertions.tuple(skuCode1, 20, new BigDecimal("100.00")),
                        Assertions.tuple(skuCode2, 20, new BigDecimal("200.00"))
                );

        Assertions.assertThat(logicStore.getPhysicalStore().getGoodsList())
                .extracting("skuCode", "qty", "price")
                .contains(
                        Assertions.tuple(skuCode1, 50, new BigDecimal("100.00")),
                        Assertions.tuple(skuCode2, 50, new BigDecimal("200.00"))
                );

        Assertions.assertThat(logicStore.getPhysicalStore().getWarehouseInfo().getUsedCapacity())
                .isEqualTo(100);

        Assertions.assertThat(order.getSendDate())
                .isNotNull();
        Assertions.assertThat(order.getSender())
                .isNotNull()
                .hasNoNullFieldsOrProperties();
        Assertions.assertThat(order.getAcceptor())
                .isNotNull()
                .hasNoNullFieldsOrProperties();
        Assertions.assertThat(order.getOrderGoodsList())
                .isNotNull()
                .isNotEmpty();
    }

    @Test
    public void transfer() throws Exception {
        // TODO
    }

}