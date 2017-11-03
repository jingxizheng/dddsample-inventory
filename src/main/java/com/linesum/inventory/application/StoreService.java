package com.linesum.inventory.application;

import com.linesum.inventory.domain.model.order.ContactId;
import com.linesum.inventory.domain.model.order.OrderId;
import com.linesum.inventory.domain.model.store.Goods;
import com.linesum.inventory.domain.model.store.LogicStore;
import com.linesum.inventory.domain.model.store.SalesStore;

import java.util.List;

public interface StoreService {

    /**
     * 商品入库
     */
    OrderId transferInLogicStore(List<Goods> goodsList, LogicStore.LogicStoreId logicStoreId, ContactId senderId);

    /**
     * 商品出库
     */
    OrderId transferOutLogicStore(List<Goods> goodsList, LogicStore.LogicStoreId logicStoreId, ContactId acceptorId);

    /**
     * 库存切割
     */
    OrderId transferLogicStore(List<Goods> goodsList, LogicStore.LogicStoreId from, LogicStore.LogicStoreId to);

    /**
     * 查询
     */
    SalesStore findSalesStore(SalesStore.SalesStoreId salesStoreId);
}
