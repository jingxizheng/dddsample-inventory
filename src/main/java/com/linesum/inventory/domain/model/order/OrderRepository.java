package com.linesum.inventory.domain.model.order;

public interface OrderRepository {

    Order find(OrderId orderId);

    OrderId save(Order order);
}
