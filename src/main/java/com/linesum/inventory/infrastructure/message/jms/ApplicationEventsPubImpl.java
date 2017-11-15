package com.linesum.inventory.infrastructure.message.jms;

import com.linesum.inventory.application.ApplicationEventsPub;
import com.linesum.inventory.domain.model.order.OrderId;

public class ApplicationEventsPubImpl implements ApplicationEventsPub {

    @Override
    public void inStoreStart(OrderId orderId) {
        // TODO
    }

    @Override
    public void outStoreStart(OrderId orderId) {
        // TODO
    }
}
