package com.linesum.inventory.domain.model.storeconfig;

import com.linesum.inventory.domain.shared.DomainEvent;
import com.linesum.inventory.domain.shared.ValueObject;

/**
 * 库存规则处理器
 */
public interface StoreConfigHandler extends DomainEvent<StoreConfig> {

    void handleConfig(StoreConfig storeConfig);

}
