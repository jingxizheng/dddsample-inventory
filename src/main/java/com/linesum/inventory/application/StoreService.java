package com.linesum.inventory.application;

import com.linesum.inventory.domain.model.order.LogicOrder;
import com.linesum.inventory.domain.model.store.*;

import java.util.List;

public interface StoreService {

    /**
     * 商品入库
     */
    LogicOrder transferInLogicStore(List<SkuCode> skuCodeList, WarehouseId warehouseId);

    /**
     * 商品出库
     */
    LogicOrder transferOutLogicStore(List<SkuCode> skuCodeList, WarehouseId warehouseId);

    /**
     * 库存切割
     */
    LogicOrder transferLogicStore(List<SkuCode> skuCodeList, WarehouseId from, WarehouseId to);


}
