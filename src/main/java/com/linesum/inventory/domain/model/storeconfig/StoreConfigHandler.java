package com.linesum.inventory.domain.model.storeconfig;

import com.linesum.inventory.domain.model.store.Goods;
import java.util.List;

/**
 * 库存规则处理器
 */
public interface StoreConfigHandler {

    List<Goods> handleConfig(StoreConfig storeConfig, List<Goods> goodsListSeed);

    boolean sameConfigAs(StoreConfig other);

}
