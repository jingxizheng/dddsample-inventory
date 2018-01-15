package com.linesum.inventory.domain.model.store;

import com.linesum.inventory.domain.model.order.Contact;
import com.linesum.inventory.domain.model.order.Order;
import com.linesum.inventory.domain.model.order.OrderId;
import com.linesum.inventory.domain.shared.Entity;
import com.linesum.inventory.domain.shared.ValueObject;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 逻辑库存
 */
public class LogicStore implements Entity<LogicStore> {

    private LogicStoreId logicStoreId;

    private StoreType storeType = StoreType.TYPE_LOGIC;

    private List<Goods> goodsList;

    private PhysicalStore physicalStore; // 所属物理库存

    public LogicStore(LogicStoreId logicStoreId, List<Goods> goodsList, PhysicalStore physicalStore) {
        this.logicStoreId = logicStoreId;
        this.goodsList = goodsList;
        this.physicalStore = physicalStore;
    }

    public Order inStore(List<Goods> pendingGoodsList, OrderId orderId, Contact sender) {
        this.physicalStore.inStore(pendingGoodsList);
        add(pendingGoodsList);
        return new Order(
                orderId,
                this.physicalStore.getWarehouseInfo().getContact(),
                sender,
                pendingGoodsList,
                new Date());
    }

    public void add(List<Goods> pendingGoodsList) {
        for (Goods addGoods : pendingGoodsList) {
            for (Goods storeGoods : this.goodsList) {
                if (addGoods.sameIdentityAs(storeGoods)) {
                    storeGoods.add(addGoods.getQty());
                }
            }
        }
    }

    public Order outStore(List<Goods> pendingGoodsList, OrderId orderId, Contact acceptor) {
        this.physicalStore.outStore(pendingGoodsList);
        reduce(pendingGoodsList);
        return new Order(
                orderId,
                acceptor,
                this.physicalStore.getWarehouseInfo().getContact(),
                pendingGoodsList,
                new Date());
    }

    public void reduce(List<Goods> pendingGoodsList) {
        for (Goods reduceGoods : pendingGoodsList) {
            for (Goods storeGoods : this.goodsList) {
                if (reduceGoods.sameIdentityAs(storeGoods)) {
                    storeGoods.reduce(reduceGoods.getQty());
                }
            }
        }
    }

    /**
     * SUGGEST better implement in domain service
     * @deprecated
     * @see com.linesum.inventory.domain.service.TransferService
     */
    public Order transfer(List<Goods> pendingGoodsList, OrderId orderId, LogicStore from) throws TransferException {
        if (!this.somePhysicalStoreAs(from)) {
            throw new TransferException(from.physicalStore.getWarehouseId(), this.physicalStore.getWarehouseId());
        }
        this.add(pendingGoodsList);
        from.reduce(pendingGoodsList);
        return new Order(
                orderId,
                this.physicalStore.getWarehouseInfo().getContact(),
                from.physicalStore.getWarehouseInfo().getContact(),
                pendingGoodsList,
                new Date());
    }

    /**
     * 判断是否在同一个物理仓库中
     */
    public boolean somePhysicalStoreAs(LogicStore other) {
        return other != null && this.physicalStore.sameIdentityAs(other.physicalStore);
    }

    @Override
    public boolean sameIdentityAs(LogicStore other) {
        return other != null && this.logicStoreId.sameValueAs(other.getLogicStoreId());
    }

    public LogicStoreId getLogicStoreId() {
        return logicStoreId;
    }

    public StoreType getStoreType() {
        return storeType;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public PhysicalStore getPhysicalStore() {
        return physicalStore;
    }

    public static class LogicStoreId implements ValueObject<LogicStoreId> {

        private Long id;

        public LogicStoreId(Long id) {
            this.id = id;
        }

        public Long getId() {
            return id;
        }

        @Override
        public boolean sameValueAs(LogicStoreId other) {
            return other != null && Objects.equals(this.id, other.getId());
        }
    }
}
