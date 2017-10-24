package com.linesum.inventory.domain.model.store;

import com.google.common.base.Preconditions;

import java.util.List;

/**
 * 物理库存
 */
public class PhysicalStore extends AbstractStore {

    private StoreType storeType = StoreType.TYPE_WARE;


    private WarehouseInfo warehouseInfo;

    public PhysicalStore(List<Goods> goodsList, List<Goods> pendingGoodsList) {
        super(goodsList, pendingGoodsList);
    }

    public WarehouseId getWarehouseId() {
        return super.warehouseId;
    }

    public WarehouseInfo getWarehouseInfo() {
        return warehouseInfo;
    }

    /**
     * 计算商品数量总和
     */
    private Integer calculateTotalPendingGoodsQty() {
        return super.pendingGoodsList.stream().
                mapToInt(Goods::getQty).
                sum();
    }

    public void inStore() {
        final Integer totalAddGoodsQty = this.calculateTotalPendingGoodsQty();
        Preconditions.checkArgument(this.warehouseInfo.enoughTotalCapacity(totalAddGoodsQty),
                "warehouse capacity does not enough");
        super.add();
    }

    public void outStore() {
        final Integer totalReduceGoodsQty = this.calculateTotalPendingGoodsQty();
        Preconditions.checkArgument(this.warehouseInfo.enoughUsedCapacity(totalReduceGoodsQty),
                "warehouse store quantity does not enough");
        super.reduce();
    }

}
