package com.linesum.inventory.application.impl;

import com.linesum.inventory.application.ApplicationEvents;
import com.linesum.inventory.application.StoreService;
import com.linesum.inventory.domain.model.order.*;
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

    @Autowired
    private ContactRepository contactRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderId transferInLogicStore(List<Goods> goodsList,
                                           LogicStore.LogicStoreId logicStoreId,
                                           ContactId senderId) {
        LogicStore logicStore = logicStoreRepository.find(logicStoreId);
        PhysicalStore physicalStore = physicalStoreRepository.find(logicStore.getPhysicalStore().getPhysicalStoreId());
        logicStore = new LogicStore(
                logicStore.getLogicStoreId(),
                logicStore.getGoodsList(),
                physicalStore);

        Contact sender = contactRepository.find(senderId);

        Order order = logicStore.inStore(goodsList, null, sender);
        OrderId orderId = orderRepository.save(order);
        applicationEvents.transferInPhysicalStore(orderId);
        return orderId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderId transferOutLogicStore(List<Goods> goodsList,
                                            LogicStore.LogicStoreId logicStoreId,
                                            ContactId acceptorId) {
        LogicStore logicStore = logicStoreRepository.find(logicStoreId);
        PhysicalStore physicalStore = physicalStoreRepository.find(logicStore.getPhysicalStore().getPhysicalStoreId());
        logicStore = new LogicStore(
                logicStore.getLogicStoreId(),
                logicStore.getGoodsList(),
                physicalStore);

        Contact acceptor = contactRepository.find(acceptorId);

        Order order = logicStore.outStore(goodsList, null, acceptor);
        OrderId orderId = orderRepository.save(order);
        applicationEvents.transferOutPhysicalStore(orderId);
        return orderId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderId transferLogicStore(List<Goods> goodsList,
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
        Order order = logicStoreTo.transfer(goodsList, null, logicStoreFrom);
        return orderRepository.save(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SalesStore findSalesStore(SalesStore.SalesStoreId salesStoreId) {
        // TODO
        return null;
    }

}
