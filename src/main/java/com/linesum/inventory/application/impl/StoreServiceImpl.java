package com.linesum.inventory.application.impl;

import com.linesum.inventory.application.ApplicationEventsPub;
import com.linesum.inventory.application.StoreService;
import com.linesum.inventory.domain.model.order.*;
import com.linesum.inventory.domain.model.store.*;
import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StoreServiceImpl implements StoreService {

    @Resource
    private PhysicalStoreRepository physicalStoreRepository;

    @Resource
    private LogicStoreRepository logicStoreRepository;

    @Resource
    private OrderRepository orderRepository;

    @Resource
    private ApplicationEventsPub applicationEventsPub;

    @Resource
    private ContactRepository contactRepository;

    @Resource
    private SalesStoreRepository salesStoreRepositoryImpl;

    @Resource
    private SalesStoreRepository salesStoreRepositoryRedisImpl;

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

        logicStoreRepository.save(logicStore);
        physicalStoreRepository.save(physicalStore);

        OrderId orderId = orderRepository.save(order);
        applicationEventsPub.inStoreStart(orderId);
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
        logicStoreRepository.save(logicStore);
        physicalStoreRepository.save(physicalStore);
        OrderId orderId = orderRepository.save(order);
        applicationEventsPub.outStoreStart(orderId);
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

        LogicStore logicStoreTo = logicStoreRepository.find(to);
        PhysicalStore physicalStoreTo = physicalStoreRepository.find(logicStoreTo.getPhysicalStore().getPhysicalStoreId());
        logicStoreTo = new LogicStore(
                logicStoreTo.getLogicStoreId(),
                logicStoreTo.getGoodsList(),
                physicalStoreTo);
        Order order = logicStoreTo.transfer(goodsList, null, logicStoreFrom);

        logicStoreRepository.save(logicStoreTo);

        logicStoreRepository.save(logicStoreFrom);

        return orderRepository.save(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SalesStore findSalesStore(SalesStore.SalesStoreId salesStoreId) {
        Optional<SalesStore> salesStoreOptional = Optional.ofNullable(salesStoreRepositoryRedisImpl.find(salesStoreId));
        if (salesStoreOptional.isPresent()) {
            return salesStoreOptional.get();
        } else {
            SalesStore salesStore = salesStoreRepositoryImpl.find(salesStoreId);
            salesStoreRepositoryRedisImpl.save(salesStore);
            return salesStore;
        }
    }

}
