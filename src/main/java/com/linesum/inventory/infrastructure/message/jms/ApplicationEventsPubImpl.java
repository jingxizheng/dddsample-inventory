package com.linesum.inventory.infrastructure.message.jms;

import com.linesum.inventory.application.ApplicationEventsPub;
import com.linesum.inventory.domain.model.order.OrderId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class ApplicationEventsPubImpl implements ApplicationEventsPub {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void inStoreStart(OrderId orderId) {
        jmsTemplate.convertAndSend("in_store_start", orderId.getId().toString());
    }

    @Override
    public void outStoreStart(OrderId orderId) {
        jmsTemplate.convertAndSend("out_store_start", orderId.getId().toString());
    }
}
