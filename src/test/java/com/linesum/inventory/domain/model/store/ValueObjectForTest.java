package com.linesum.inventory.domain.model.store;

import com.linesum.inventory.domain.shared.ValueObject;

import java.util.Objects;

public final class ValueObjectForTest implements ValueObject<ValueObjectForTest> {

    private String prop1;

    private int prop2;

    public ValueObjectForTest(String prop1, int prop2) {
        this.prop1 = prop1;
        this.prop2 = prop2;
    }

    @Override
    public boolean sameValueAs(ValueObjectForTest other) {
        return other != null &&
                Objects.equals(this.prop1, other.prop1) &&
                Objects.equals(this.prop2, other.prop2);
    }
}
