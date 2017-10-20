package com.linesum.inventory.domain.model.store;

import com.google.common.base.Preconditions;
import com.linesum.inventory.domain.shared.Entity;
import com.linesum.inventory.domain.shared.ValueObject;

import java.util.List;
import java.util.Objects;

/**
 * 物理库存
 */
public class PhysicalStore extends AbstractStore implements Entity<PhysicalStore> {

    private WarehouseId warehouseId;

    private StoreType storeType;

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

    public enum StoreType implements ValueObject<StoreType>{
        EXTERNAL, // 外部仓库
        OWN, // 自有仓库
        OFFLINE_SHOP; //线下门店仓库

        @Override
        public boolean sameValueAs(StoreType other) {
            return other != null && Objects.equals(this, other);
        }
    }

}
