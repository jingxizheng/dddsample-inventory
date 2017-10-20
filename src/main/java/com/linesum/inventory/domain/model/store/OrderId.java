package com.linesum.inventory.domain.model.store;

import com.google.common.base.Preconditions;
import com.linesum.inventory.domain.shared.ValueObject;

import java.util.Objects;

/**
 * 订单ID
 */
public class OrderId implements ValueObject<OrderId> {

    private String id;

    public String idString() {
        return id;
    }

    public OrderId(String id) {
        Preconditions.checkNotNull(id, "id is required");
        this.id = id;
    }

    @Override
    public boolean sameValueAs(OrderId other) {
        return other != null && Objects.equals(this.id, other.id);
    }
}
