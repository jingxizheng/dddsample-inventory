package com.linesum.inventory.domain.model.logistics;

import com.linesum.inventory.domain.shared.ValueObject;

import java.util.Objects;

/**
 * 快递单号
 */
public class LogisticsId implements ValueObject<LogisticsId> {

    private Long id;

    @Override
    public boolean sameValueAs(LogisticsId other) {
        return other != null && Objects.equals(this.id, other.id);
    }

}
