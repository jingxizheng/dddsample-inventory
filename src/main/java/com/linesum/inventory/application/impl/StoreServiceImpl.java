package com.linesum.inventory.application.impl;

import com.linesum.inventory.application.StoreService;
import com.linesum.inventory.domain.model.order.LogicOrder;
import com.linesum.inventory.domain.model.order.OrderRepository;
import com.linesum.inventory.domain.model.store.AbstractStore;
import com.linesum.inventory.domain.model.store.Goods;
import com.linesum.inventory.domain.model.store.StoreType;
import com.linesum.inventory.domain.model.store.WarehouseId;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class StoreServiceImpl implements StoreService {

    @Override
    public LogicOrder transferInLogicStore(List<Goods> goodsList, WarehouseId warehouseId) {
        return null;
    }

    @Override
    public LogicOrder transferOutLogicStore(List<Goods> goodsList, WarehouseId warehouseId) {
        return null;
    }

    @Override
    public LogicOrder transferLogicStore(List<Goods> goodsList, WarehouseId from, WarehouseId to) {
        return null;
    }

    @Override
    public List<AbstractStore> queryStoreByType(StoreType type) {
        return null;
    }

}
