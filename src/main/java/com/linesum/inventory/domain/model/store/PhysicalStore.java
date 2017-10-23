package com.linesum.inventory.domain.model.store;

import com.google.common.base.Preconditions;
import com.linesum.inventory.domain.shared.Entity;

import java.util.List;

/**
 * 物理库存
 */
public class PhysicalStore extends AbstractStore implements Entity<PhysicalStore> {

    private StoreType storeType = StoreType.TYPE_WARE;

    private WarehouseId warehouseId;

    private WarehouseInfo warehouseInfo;

    public PhysicalStore(List<Goods> goodsList, List<Goods> pendingGoodsList) {
        super(goodsList, pendingGoodsList);
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
        // TODO 通知仓库管理员
    }

    public void outStore() {
        final Integer totalReduceGoodsQty = this.calculateTotalPendingGoodsQty();
        Preconditions.checkArgument(this.warehouseInfo.enoughUsedCapacity(totalReduceGoodsQty),
                "warehouse store quantity does not enough");
        super.reduce();
        // TODO 通知仓库管理员
    }

    @Override
    public boolean sameIdentityAs(PhysicalStore other) {
        return other != null && this.warehouseId.sameValueAs(other.warehouseId);
    }

}
