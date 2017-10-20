package com.linesum.inventory.domain.model.store;

import com.linesum.inventory.domain.shared.ValueObject;

import java.util.List;
import java.util.Objects;

/**
 * 物理库存
 */
public class PhysicalStore extends AbstractStore<PhysicalStore> {

    private StoreType storeType;

    public PhysicalStore(WarehouseId warehouseId, WarehouseInfo warehouseInfo, List<Goods> goodsList) {
        super(warehouseId, warehouseInfo, goodsList);
    }

    public PhysicalStore(WarehouseId warehouseId, WarehouseInfo warehouseInfo, List<Goods> goodsList, StoreType storeType) {
        super(warehouseId, warehouseInfo, goodsList);
        this.storeType = storeType;
    }

    public PhysicalOrder inStore(List<Goods> inStoreGoodsList) {
        super.add(inStoreGoodsList);
        // TODO 生成收货单据 -> 通知盘点
        return null;
    }

    public PhysicalOrder outStore(List<Goods> outStoreGoodsList) {
        super.reduce(outStoreGoodsList);
        // TODO 生成发货单据 -> 通知物流
        return null;
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
