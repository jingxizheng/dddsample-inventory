package com.linesum.inventory.domain.model.store;

import com.linesum.inventory.domain.shared.ValueObject;

import java.util.List;
import java.util.Objects;

/**
 * 逻辑库存
 */
public class LogicStore extends AbstractStore<LogicStore> {

    private LogicStore storeType;

    private PhysicalStore physicalStore;

    public LogicStore(WarehouseId warehouseId, WarehouseInfo warehouseInfo, List<Goods> goodsList) {
        super(warehouseId, warehouseInfo, goodsList);
    }

    public LogicStore(WarehouseId warehouseId, WarehouseInfo warehouseInfo, List<Goods> goodsList, LogicStore storeType) {
        super(warehouseId, warehouseInfo, goodsList);
        this.storeType = storeType;
    }

    public PhysicalOrder inStore(List<Goods> inStoreGoodsList) {
        super.add(inStoreGoodsList);
        // TODO 生成入库单据
        return null;
    }

    public PhysicalOrder outStore(List<Goods> outStoreGoodsList) {
        super.reduce(outStoreGoodsList);
        // TODO 生成出库单据
        return null;
    }


    public enum StoreType implements ValueObject<StoreType>{
        SALES, // 销售仓库
        DEFECTIVE, // 次品仓库
        RETURN; // 退货仓库

        @Override
        public boolean sameValueAs(StoreType other) {
            return other != null && Objects.equals(this, other);
        }
    }
}
