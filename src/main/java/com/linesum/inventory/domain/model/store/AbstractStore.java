package com.linesum.inventory.domain.model.store;

import com.google.common.base.Preconditions;
import com.linesum.inventory.domain.shared.Entity;

import java.util.List;

/**
 * 抽象库存
 */
public abstract class AbstractStore<S extends AbstractStore> implements Entity<S> {

    protected WarehouseId warehouseId;

    protected WarehouseInfo warehouseInfo;

    protected List<Goods> goodsList;

    public AbstractStore(WarehouseId warehouseId, WarehouseInfo warehouseInfo, List<Goods> goodsList) {
        Preconditions.checkNotNull(warehouseId, "warehouseId is required");
        Preconditions.checkNotNull(warehouseInfo, "warehouseInfo is required");
        Preconditions.checkNotNull(goodsList, "goodsList is required");
        this.warehouseId = warehouseId;
        this.warehouseInfo = warehouseInfo;
        this.goodsList = goodsList;
    }

    /**
     * 计算商品数量总和
     * @return
     */
    private Integer calculateTotalGoodsQty(List<Goods> goodsList) {
        return goodsList.stream().
                mapToInt(Goods::getQty).
                sum();
    }

    /**
     * 库存增加需要检验仓库容量是否充足
     * @param addGoodsList
     */
    public void add(List<Goods> addGoodsList) {
        Preconditions.checkNotNull(addGoodsList, "addGoodsList is required");
        final Integer totalAddGoodsQty = calculateTotalGoodsQty(goodsList);
        Preconditions.checkArgument(this.warehouseInfo.enoughTotalCapacity(totalAddGoodsQty),
                "warehouse capacity does not enough");
        for (Goods addGoods : addGoodsList) {
            for (Goods storeGoods : this.goodsList) {
                if (addGoods.sameIdentityAs(storeGoods)) {
                    storeGoods.add(addGoods.getQty());
                }
            }
        }
    }

    /**
     * 库存扣减需要检验库存是否充足
     * @param reduceGoodsList
     */
    public void reduce(List<Goods> reduceGoodsList) {
        Preconditions.checkNotNull(reduceGoodsList, "reduceGoodsList is required");
        final Integer totalReduceGoodsQty = calculateTotalGoodsQty(goodsList);
        Preconditions.checkArgument(this.warehouseInfo.enoughUsedCapacity(totalReduceGoodsQty),
                "warehouse store quantity does not enough");
        for (Goods reduceGoods : reduceGoodsList) {
            for (Goods storeGoods : this.goodsList) {
                if (reduceGoods.sameIdentityAs(storeGoods)) {
                    storeGoods.reduce(reduceGoods.getQty());
                }
            }
        }
    }

    @Override
    public boolean sameIdentityAs(S other) {
        return other != null && this.warehouseId.sameValueAs(other.warehouseId);
    }

}