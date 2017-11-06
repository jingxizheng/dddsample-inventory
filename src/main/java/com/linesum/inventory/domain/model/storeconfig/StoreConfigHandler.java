package com.linesum.inventory.domain.model.storeconfig;

import com.linesum.inventory.domain.model.store.Goods;
import com.linesum.inventory.domain.shared.DomainEvent;
import com.linesum.inventory.domain.shared.ValueObject;

import java.util.List;

/**
 * 库存规则处理器
 */
public interface StoreConfigHandler extends DomainEvent<StoreConfig> {

    List<Goods> handleConfig(StoreConfig storeConfig, List<Goods> goodsListSeed);

}
