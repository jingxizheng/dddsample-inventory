package com.linesum.inventory.domain.model.store;

import com.google.common.base.Preconditions;
import com.linesum.inventory.domain.shared.Entity;

import java.util.Collections;
import java.util.List;

/**
 * 抽象库存
 */
public abstract class AbstractStore implements Entity<AbstractStore> {

    protected WarehouseId warehouseId;

    protected List<Goods> goodsList;

    protected List<Goods> pendingGoodsList;

    public AbstractStore(List<Goods> goodsList, List<Goods> pendingGoodsList) {
        Preconditions.checkNotNull(goodsList, "goodsList is required");
        Preconditions.checkNotNull(pendingGoodsList, "pendingGoodsList is required");
        this.goodsList = goodsList;
        this.pendingGoodsList = pendingGoodsList;
    }

    public AbstractStore() {
        this.goodsList = Collections.EMPTY_LIST;
        this.pendingGoodsList = Collections.EMPTY_LIST;
    }

    /**
     * 库存增加需要检验仓库容量是否充足
     */
    public void add() {
        for (Goods addGoods : pendingGoodsList) {
            for (Goods storeGoods : this.goodsList) {
                if (addGoods.sameIdentityAs(storeGoods)) {
                    storeGoods.add(addGoods.getQty());
                }
            }
        }
    }

    /**
     * 库存扣减需要检验库存是否充足
     */
    public void reduce() {
        for (Goods reduceGoods : pendingGoodsList) {
            for (Goods storeGoods : this.goodsList) {
                if (reduceGoods.sameIdentityAs(storeGoods)) {
                    storeGoods.reduce(reduceGoods.getQty());
                }
            }
        }
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    @Override
    public boolean sameIdentityAs(AbstractStore other) {
        return other != null && this.warehouseId.sameValueAs(other.warehouseId);
    }

}