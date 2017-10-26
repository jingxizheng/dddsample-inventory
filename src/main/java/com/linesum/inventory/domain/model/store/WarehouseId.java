package com.linesum.inventory.domain.model.store;

import com.google.common.base.Preconditions;
import com.linesum.inventory.domain.shared.ValueObject;

import java.util.Objects;

/**
 * 仓库ID 仓库的唯一标识 由应用程序自动生成
 */
public class WarehouseId implements ValueObject<WarehouseId> {

    private Long id;

    public String idStrign() {
        return id.toString();
    }

    public Long getId() {
        return id;
    }

    public WarehouseId(Long id) {
        this.id = id;
    }

    @Override
    public boolean sameValueAs(WarehouseId other) {
        return other != null && Objects.equals(this.id, other.id);
    }
}
