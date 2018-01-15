package com.linesum.inventory.domain.repository;

import com.linesum.inventory.domain.model.order.Order;
import com.linesum.inventory.domain.model.order.OrderId;

public interface OrderRepository {

    Order find(OrderId orderId);

    OrderId save(Order order);
}
