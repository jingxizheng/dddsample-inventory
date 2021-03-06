package com.linesum.inventory.domain.model.order;

import com.linesum.inventory.domain.shared.ValueObject;

import java.util.Objects;

/**
 * 订单ID
 */
public class OrderId implements ValueObject<OrderId> {

    private Long id;

    public OrderId(Long id) {
        this.id = id;
    }

    public String idString() {
        return id.toString();
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean sameValueAs(OrderId other) {
        return other != null && Objects.equals(this.id, other.id);
    }
}
