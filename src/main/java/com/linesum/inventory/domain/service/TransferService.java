package com.linesum.inventory.domain.service;

import com.linesum.inventory.domain.model.order.Order;
import com.linesum.inventory.domain.model.order.OrderId;
import com.linesum.inventory.domain.model.store.Goods;
import com.linesum.inventory.domain.model.store.LogicStore;
import com.linesum.inventory.domain.model.store.TransferException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 移仓服务
 *
 * 领域服务的特性
 * 1. 执行一个显著的业务操作过程
 * 2. 对领域对象进行转换
 * 3. 以多个领域对象作为输入进行计算，结果产生一个值对象
 *
 * 领域服务是显著的业务过程，但又难以将这种过程作为某一领域对象的行为时，这时就应该将它们放在领域服务中
 *
 * 比如移仓这个方法，感觉放在移出方(from)或已入方(to)都觉得别扭时，就应该放在领域服务中
 */
@Service
public class TransferService {

    public Order transfer(List<Goods> pendingGoodsList, OrderId orderId, LogicStore from, LogicStore to) throws TransferException {
        if (!to.somePhysicalStoreAs(from)) {
            throw new TransferException(from.getPhysicalStore().getWarehouseId(), to.getPhysicalStore().getWarehouseId());
        }
        to.add(pendingGoodsList);
        from.reduce(pendingGoodsList);
        return new Order(
                orderId,
                to.getPhysicalStore().getWarehouseInfo().getContact(),
                from.getPhysicalStore().getWarehouseInfo().getContact(),
                pendingGoodsList,
                new Date());
    }
}
