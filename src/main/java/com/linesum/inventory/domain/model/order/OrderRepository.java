package com.linesum.inventory.domain.model.order;

import java.util.List;

public interface OrderRepository<O extends AbstractOrder> {

    O find(OrderId orderId);

    List<O> findAll();

    void save(O order);

    OrderId nextOrderId();
}
