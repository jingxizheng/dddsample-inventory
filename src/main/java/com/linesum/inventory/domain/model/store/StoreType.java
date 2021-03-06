package com.linesum.inventory.domain.model.store;

import com.linesum.inventory.domain.shared.ValueObject;

import java.util.Objects;

public enum StoreType implements ValueObject<StoreType> {
    TYPE_WARE, TYPE_LOGIC, TYPE_SALES;

    @Override
    public boolean sameValueAs(StoreType other) {
        return other != null && Objects.equals(this, other);
    }
}