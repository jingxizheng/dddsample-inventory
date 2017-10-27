package com.linesum.inventory.application.impl;

import com.linesum.inventory.application.ApplicationEvents;
import com.linesum.inventory.application.StoreService;
import com.linesum.inventory.domain.model.order.Contact;
import com.linesum.inventory.domain.model.order.Order;
import com.linesum.inventory.domain.model.order.OrderId;
import com.linesum.inventory.domain.model.order.OrderRepository;
import com.linesum.inventory.domain.model.store.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class StoreServiceImpl implements StoreService {

    @Autowired
    private PhysicalStoreRepository physicalStoreRepository;

    @Autowired
    private LogicStoreRepository logicStoreRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ApplicationEvents applicationEvents;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order transferInLogicStore(List<Goods> goodsList,
                                           LogicStore.LogicStoreId logicStoreId,
                                           Contact sender) {
        LogicStore logicStore = logicStoreRepository.find(logicStoreId);
        PhysicalStore physicalStore = physicalStoreRepository.find(logicStore.getPhysicalStore().getPhysicalStoreId());
        logicStore = new LogicStore(
                logicStore.getLogicStoreId(),
                logicStore.getGoodsList(),
                physicalStore);

        Order order = logicStore.inStore(goodsList, null, sender);
        OrderId orderId = orderRepository.save(order);
        applicationEvents.transferInPhysicalStore(orderId);
        return order.addOrderId(orderId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order transferOutLogicStore(List<Goods> goodsList,
                                            LogicStore.LogicStoreId logicStoreId,
                                            Contact acceptor) {
        LogicStore logicStore = logicStoreRepository.find(logicStoreId);
        PhysicalStore physicalStore = physicalStoreRepository.find(logicStore.getPhysicalStore().getPhysicalStoreId());
        logicStore = new LogicStore(
                logicStore.getLogicStoreId(),
                logicStore.getGoodsList(),
                physicalStore);

        Order order = logicStore.outStore(goodsList, null, acceptor);
        OrderId orderId = orderRepository.save(order);
        applicationEvents.transferOutPhysicalStore(orderId);
        return order.addOrderId(orderId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order transferLogicStore(List<Goods> goodsList,
                                         LogicStore.LogicStoreId from,
                                         LogicStore.LogicStoreId to) {
        LogicStore logicStoreFrom = logicStoreRepository.find(from);
        PhysicalStore physicalStoreFrom = physicalStoreRepository.find(logicStoreFrom.getPhysicalStore().getPhysicalStoreId());
        logicStoreFrom = new LogicStore(
                logicStoreFrom.getLogicStoreId(),
                logicStoreFrom.getGoodsList(),
                physicalStoreFrom);

        LogicStore logicStoreTo = logicStoreRepository.find(from);
        PhysicalStore physicalStoreTo = physicalStoreRepository.find(logicStoreTo.getPhysicalStore().getPhysicalStoreId());
        logicStoreTo = new LogicStore(
                logicStoreTo.getLogicStoreId(),
                logicStoreTo.getGoodsList(),
                physicalStoreTo);

        return logicStoreTo.transfer(goodsList, null, logicStoreFrom);
    }

}
