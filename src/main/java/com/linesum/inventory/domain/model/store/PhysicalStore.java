package com.linesum.inventory.domain.model.store;

import com.google.common.base.Preconditions;
import com.linesum.inventory.domain.shared.Entity;
import com.linesum.inventory.domain.shared.ValueObject;

import java.util.List;
import java.util.Objects;

/**
 * 物理库存
 */
public class PhysicalStore implements Entity<PhysicalStore> {

    private PhysicalStoreId physicalStoreId;

    private StoreType storeType = StoreType.TYPE_WARE;

    private WarehouseId warehouseId;

    private WarehouseInfo warehouseInfo;

    private List<Goods> goodsList;

    public PhysicalStore(PhysicalStoreId physicalStoreId, WarehouseId warehouseId, WarehouseInfo warehouseInfo, List<Goods> goodsList) {
        this.physicalStoreId = physicalStoreId;
        this.warehouseId = warehouseId;
        this.warehouseInfo = warehouseInfo;
        this.goodsList = goodsList;
    }

    /**
     * 计算商品数量总和
     */
    private Integer calculateTotalGoodsQty(List<Goods> goodsList) {
        return goodsList.stream().
                mapToInt(Goods::getQty).
                sum();
    }

    public void inStore(List<Goods> pendingGoodsList) {
        final Integer totalAddGoodsQty = this.calculateTotalGoodsQty(pendingGoodsList);
        Preconditions.checkArgument(this.warehouseInfo.enoughTotalCapacity(totalAddGoodsQty),
                "warehouse capacity does not enough");
        for (Goods addGoods : pendingGoodsList) {
            for (Goods storeGoods : this.goodsList) {
                if (addGoods.sameIdentityAs(storeGoods)) {
                    storeGoods.add(addGoods.getQty());
                }
            }
        }
        warehouseInfo.addUsedCapacity(pendingGoodsList.stream().mapToInt(Goods::getQty).sum());
    }

    public void outStore(List<Goods> pendingGoodsList) {
        final Integer totalReduceGoodsQty = this.calculateTotalGoodsQty(pendingGoodsList);
        Preconditions.checkArgument(this.warehouseInfo.enoughUsedCapacity(totalReduceGoodsQty),
                "warehouse store quantity does not enough");
        for (Goods reduceGoods : pendingGoodsList) {
            for (Goods storeGoods : this.goodsList) {
                if (reduceGoods.sameIdentityAs(storeGoods)) {
                    storeGoods.reduce(reduceGoods.getQty());
                }
            }
        }
        warehouseInfo.reduceUsedCapacity(pendingGoodsList.stream().mapToInt(Goods::getQty).sum());
    }

    @Override
    public boolean sameIdentityAs(PhysicalStore other) {
        return other != null && this.physicalStoreId.sameValueAs(other.getPhysicalStoreId());
    }

    public PhysicalStoreId getPhysicalStoreId() {
        return physicalStoreId;
    }

    public StoreType getStoreType() {
        return storeType;
    }

    public WarehouseId getWarehouseId() {
        return warehouseId;
    }

    public WarehouseInfo getWarehouseInfo() {
        return warehouseInfo;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public static class PhysicalStoreId implements ValueObject<PhysicalStoreId> {

        private Long id;

        public PhysicalStoreId(Long id) {
            this.id = id;
        }

        public Long getId() {
            return id;
        }

        @Override
        public boolean sameValueAs(PhysicalStoreId other) {
            return other != null && Objects.equals(this.id, other.getId());
        }
    }

}
