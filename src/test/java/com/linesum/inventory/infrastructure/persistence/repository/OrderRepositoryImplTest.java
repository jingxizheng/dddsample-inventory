package com.linesum.inventory.infrastructure.persistence.repository;

import com.linesum.inventory.domain.model.order.*;
import com.linesum.inventory.domain.model.store.Goods;
import com.linesum.inventory.domain.model.store.SkuCode;
import com.linesum.inventory.BaseJunitTestCase;
import com.linesum.inventory.infrastructure.persistence.jpa.ContactRepositoryJpa;
import com.linesum.inventory.infrastructure.persistence.jpa.GoodsRepositoryJpa;
import com.linesum.inventory.infrastructure.persistence.jpa.po.ContactPo;
import com.linesum.inventory.infrastructure.persistence.jpa.po.GoodsPo;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhengjx on 2017/11/2.
 */
public class OrderRepositoryImplTest extends BaseJunitTestCase {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ContactRepositoryJpa contactRepositoryJpa;

    @Autowired
    private GoodsRepositoryJpa goodsRepositoryJpa;

    private Order order;

    private GoodsPo goodsPo;

    @Before
    public void setUp() throws Exception {
        ContactPo senderPo = contactRepositoryJpa.save(new ContactPo(null, "sender", "sender_address", "sender_phone"));
        ContactPo acceptorPo = contactRepositoryJpa.save(new ContactPo(null, "acceptor", "acceptor_address", "acceptor_phone"));

        goodsPo = goodsRepositoryJpa.save(new GoodsPo(null, "sku_code", new BigDecimal("100.00")));

        order = new Order(
                new OrderId(null),
                new Contact(
                        new ContactId(acceptorPo.getId()),
                        acceptorPo.getName(),
                        acceptorPo.getAddress(),
                        acceptorPo.getTelephone()
                ),
                new Contact(
                        new ContactId(senderPo.getId()),
                        acceptorPo.getName(),
                        acceptorPo.getAddress(),
                        acceptorPo.getTelephone()
                ),
                Lists.newArrayList(
                        new Goods(new SkuCode(goodsPo.getSkuCode()), 100, goodsPo.getPrice())
                ),
                new Date()
        );
    }

    @Test
    public void test() throws Exception {
        OrderId orderId = orderRepository.save(order);
        Assertions.assertThat(order)
                .isNotNull()
                .hasNoNullFieldsOrPropertiesExcept("acceptDate");

        Order order = orderRepository.find(orderId);
        Assertions.assertThat(order)
                .isNotNull()
                .hasNoNullFieldsOrPropertiesExcept("acceptDate");
        Assertions.assertThat(order.getSender())
                .isNotNull()
                .hasNoNullFieldsOrProperties();
        Assertions.assertThat(order.getAcceptor())
                .isNotNull()
                .hasNoNullFieldsOrProperties();
        Assertions.assertThat(order.getOrderGoodsList())
                .isNotNull()
                .isNotEmpty()
                .first()
                .hasFieldOrPropertyWithValue("qty", 100)
                .hasFieldOrPropertyWithValue("price", goodsPo.getPrice());
    }

}