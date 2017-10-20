package com.linesum.inventory.domain.model.store;

import com.linesum.inventory.domain.shared.ValueObject;
import com.sun.org.apache.bcel.internal.classfile.Code;

import java.util.Objects;

/**
 * sku
 */
public class SkuCode implements ValueObject<SkuCode> {

    private String code;

    public String codeString() {
        return this.code;
    }

    @Override
    public boolean sameValueAs(SkuCode other) {
        return other != null && Objects.equals(this.code, other.code);
    }
}
