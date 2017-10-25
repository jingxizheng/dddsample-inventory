package com.linesum.inventory.application.impl;

import com.linesum.inventory.application.StoreService;
import com.linesum.inventory.domain.model.order.LogicOrder;
import com.linesum.inventory.domain.model.store.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class StoreServiceImpl implements StoreService {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private PhysicalStoreRepository physicalStoreRepository;

    @Autowired
    private LogicStoreRepository logicStoreRepository;

    @Override
    public LogicOrder transferInLogicStore(List<SkuCode> skuCodeList, WarehouseId warehouseId) {
        LogicStore logicStore = logicStoreRepository.find(warehouseId);

        List<Goods> goodsList = goodsRepository.find(skuCodeList);


        return null;
    }

    @Override
    public LogicOrder transferOutLogicStore(List<SkuCode> skuCodeList, WarehouseId warehouseId) {
        return null;
    }

    @Override
    public LogicOrder transferLogicStore(List<SkuCode> skuCodeList, WarehouseId from, WarehouseId to) {
        return null;
    }

}
