package com.linesum.inventory.application;

import com.linesum.inventory.domain.model.order.Contact;
import com.linesum.inventory.domain.model.order.Order;
import com.linesum.inventory.domain.model.store.Goods;
import com.linesum.inventory.domain.model.store.LogicStore;

import java.util.List;

public interface StoreService {

    /**
     * 商品入库
     */
    Order transferInLogicStore(List<Goods> goodsList, LogicStore.LogicStoreId logicStoreId, Contact sender);

    /**
     * 商品出库
     */
    Order transferOutLogicStore(List<Goods> goodsList, LogicStore.LogicStoreId logicStoreId, Contact acceptor);

    /**
     * 库存切割
     */
    Order transferLogicStore(List<Goods> goodsList, LogicStore.LogicStoreId from, LogicStore.LogicStoreId to);


}
