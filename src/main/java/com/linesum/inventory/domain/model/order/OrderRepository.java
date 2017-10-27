package com.linesum.inventory.domain.model.order;

import java.util.List;

public interface OrderRepository {

    Order find(OrderId orderId);

    List<Order> findAll();

    OrderId save(Order order);
}
