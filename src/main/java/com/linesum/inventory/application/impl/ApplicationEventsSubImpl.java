package com.linesum.inventory.application.impl;

import com.linesum.inventory.application.ApplicationEventsSub;
import com.linesum.inventory.domain.model.handing.HandingEvent;
import com.linesum.inventory.domain.model.order.Order;
import com.linesum.inventory.domain.model.order.OrderId;
import com.linesum.inventory.domain.repository.OrderRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class ApplicationEventsSubImpl implements ApplicationEventsSub {

    @Resource
    private OrderRepository orderRepository;

    @Override
    public void inStoreDone(OrderId orderId, Date acceptDate) {
        Order order = orderRepository.find(orderId);
        HandingEvent handingEvent = new HandingEvent(order, HandingEvent.Type.IN_STORE_DONE, acceptDate);

        orderRepository.save(handingEvent.getOrder());
    }

    @Override
    public void outStoreDone(OrderId orderId, Date acceptDate) {
        Order order = orderRepository.find(orderId);
        HandingEvent handingEvent = new HandingEvent(order, HandingEvent.Type.OUT_STORE_DONE, acceptDate);

        orderRepository.save(handingEvent.getOrder());
    }
}
