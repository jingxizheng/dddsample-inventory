package com.linesum.inventory.domain.model.store;

import com.google.common.base.Preconditions;
import com.linesum.inventory.domain.model.order.PhysicalOrder;
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

    /**
     * 计算商品数量总和
     */
    private Integer calculateTotalPendingGoodsQty() {
        return super.pendingGoodsList.stream().
                mapToInt(Goods::getQty).
                sum();
    }

    public PhysicalOrder inStore() {
        final Integer totalAddGoodsQty = this.calculateTotalPendingGoodsQty();
        Preconditions.checkArgument(this.warehouseInfo.enoughTotalCapacity(totalAddGoodsQty),
                "warehouse capacity does not enough");
        super.add();
        // TODO 生成收货单据 -> 通知盘点
        return null;
    }

    public PhysicalOrder outStore() {
        final Integer totalReduceGoodsQty = this.calculateTotalPendingGoodsQty();
        Preconditions.checkArgument(this.warehouseInfo.enoughUsedCapacity(totalReduceGoodsQty),
                "warehouse store quantity does not enough");
        super.reduce();
        // TODO 生成发货单据 -> 通知物流
        return null;
    }

    @Override
    public boolean sameIdentityAs(PhysicalStore other) {
        return other != null && this.warehouseId.sameValueAs(other.warehouseId);
    }

}
