package com.linesum.inventory.domain.model.store;

import com.linesum.inventory.domain.model.order.Contact;
import com.linesum.inventory.domain.model.order.LogicOrder;
import com.linesum.inventory.domain.model.order.OrderId;

import java.util.Date;
import java.util.List;

/**
 * 逻辑库存
 */
public class LogicStore extends AbstractStore {

    private StoreType storeType = StoreType.TYPE_LOGIC;

    private PhysicalStore physicalStore; // 所属物理库存

    public LogicStore(List<Goods> goodsList, List<Goods> pendingGoodsList) {
        super(goodsList, pendingGoodsList);
    }

    public LogicOrder inStore(OrderId orderId, Contact sender) {
        super.add();
        this.physicalStore.inStore();
        return new LogicOrder(
                orderId,
                this.physicalStore.getWarehouseInfo().getContact(),
                sender,
                super.pendingGoodsList,
                new Date());
    }

    public LogicOrder outStore(OrderId orderId, Contact acceptor) {
        super.reduce();
        this.physicalStore.outStore();
        return new LogicOrder(
                orderId,
                acceptor,
                this.physicalStore.getWarehouseInfo().getContact(),
                super.pendingGoodsList,
                new Date());
    }

    public LogicOrder transfer(OrderId orderId, LogicStore from) throws TransferException {
        if (!this.somePhysicalStoreAs(from)) {
            throw new TransferException(from.physicalStore.getWarehouseId(), this.physicalStore.getWarehouseId());
        }
        super.add();
        from.reduce();
        return new LogicOrder(
                orderId,
                this.physicalStore.getWarehouseInfo().getContact(),
                from.physicalStore.getWarehouseInfo().getContact(),
                super.pendingGoodsList,
                new Date());
    }

    /**
     * 判断是否在同一个物理仓库中
     */
    public boolean somePhysicalStoreAs(LogicStore other) {
        return other != null && this.physicalStore.sameIdentityAs(other.physicalStore);
    }

}
