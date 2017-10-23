package com.linesum.inventory.domain.model.store;

import com.linesum.inventory.domain.model.order.LogicOrder;
import com.linesum.inventory.domain.shared.ValueObject;

import java.util.List;
import java.util.Objects;

/**
 * 逻辑库存
 */
public class LogicStore extends AbstractStore implements ValueObject<LogicStore> {

    private StoreType storeType = StoreType.TYPE_LOGIC;

    private PhysicalStore physicalStore; // 所属物理库存

    public LogicStore(List<Goods> goodsList, List<Goods> pendingGoodsList) {
        super(goodsList, pendingGoodsList);
    }

    public LogicOrder inStore() {
        super.add();
        this.physicalStore.inStore();
        // TODO 生成入库单据
        return null;
    }

    public LogicOrder outStore() {
        super.reduce();
        this.physicalStore.outStore();
        // TODO 生成出库单据
        return null;
    }

    /**
     * 判断是否在同一个物理仓库中
     */
    public boolean somePhysicalStoreAs(LogicStore other) {
        return other != null && this.physicalStore.sameIdentityAs(other.physicalStore);
    }

    @Override
    public boolean sameValueAs(LogicStore other) {
        return other != null &&
                Objects.equals(this.storeType, other.storeType) &&
                this.physicalStore.sameIdentityAs(other.physicalStore);
    }

}
