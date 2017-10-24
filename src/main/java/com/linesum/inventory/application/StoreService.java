package com.linesum.inventory.application;

import com.linesum.inventory.domain.model.order.LogicOrder;
import com.linesum.inventory.domain.model.store.*;

import java.util.List;

public interface StoreService {

    /**
     * 商品入库
     */
    LogicOrder transferInLogicStore(List<Goods> goodsList, WarehouseId warehouseId);

    /**
     * 商品出库
     */
    LogicOrder transferOutLogicStore(List<Goods> goodsList, WarehouseId warehouseId);

    /**
     * 库存切割
     */
    LogicOrder transferLogicStore(List<Goods> goodsList, WarehouseId from, WarehouseId to);

    /**
     * 查询指定类型的库存
     */
    List<AbstractStore> queryStoreByType(StoreType type);



}
