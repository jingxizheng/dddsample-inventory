package com.linesum.inventory.domain.model.store;

import com.linesum.inventory.domain.shared.ValueObject;

/**
 * 库存规则
 */
public class StoreConfig implements ValueObject<StoreConfig> {




    @Override
    public boolean sameValueAs(StoreConfig other) {
        return other != null;
    }
}
