package com.linesum.inventory.application.impl;

import com.linesum.inventory.domain.model.order.*;
import com.linesum.inventory.domain.model.store.Goods;
import com.linesum.inventory.domain.model.store.SkuCode;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class ApplicationEventsSubImplTest {

    @InjectMocks
    private ApplicationEventsSubImpl ApplicationEventsSubImpl = new ApplicationEventsSubImpl();

    @Mock
    private OrderRepository orderRepository;

    private OrderId orderId = new OrderId(1L);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        Order order = new Order(
                orderId,
                new Contact(
                        new ContactId(2L),
                        "acceptor_name",
                        "acceptor_address",
                        "acceptor_phone"
                ),
                new Contact(
                        new ContactId(2L),
                        "sender_name",
                        "sender_address",
                        "sender_phone"
                ),
                Lists.newArrayList(
                        new Goods(new SkuCode(UUID.randomUUID().toString()), 100, new BigDecimal("100.00"))
                ),
                new Date()
        );

        when(orderRepository.find(orderId)).thenReturn(order);

        when(orderRepository.save(any(Order.class))).thenReturn(orderId);
    }

    @Test
    public void inStoreDone() throws Exception {
        ApplicationEventsSubImpl.inStoreDone(orderId, new Date());

        ArgumentCaptor<OrderId> orderIdArgumentCaptor = ArgumentCaptor.forClass(OrderId.class);
        verify(orderRepository).find(orderIdArgumentCaptor.capture());
        OrderId orderIdCap = orderIdArgumentCaptor.getValue();

        Assertions.assertThat(orderIdCap.getId())
                .isEqualTo(orderId.getId());

        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(orderArgumentCaptor.capture());
        Order orderCap = orderArgumentCaptor.getValue();

        Assertions.assertThat(orderCap.getAcceptDate())
                .isNotNull();
    }



    @Test
    public void outStoreDone() throws Exception {
        ApplicationEventsSubImpl.outStoreDone(orderId, new Date());

        ArgumentCaptor<OrderId> orderIdArgumentCaptor = ArgumentCaptor.forClass(OrderId.class);
        verify(orderRepository).find(orderIdArgumentCaptor.capture());
        OrderId orderIdCap = orderIdArgumentCaptor.getValue();

        Assertions.assertThat(orderIdCap.getId())
                .isEqualTo(orderId.getId());

        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(orderArgumentCaptor.capture());
        Order orderCap = orderArgumentCaptor.getValue();

        Assertions.assertThat(orderCap.getAcceptDate())
                .isNotNull();
    }

}