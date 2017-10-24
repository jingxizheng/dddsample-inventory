package com.linesum.inventory.domain.model.storeconfig;

import com.google.common.base.Preconditions;
import com.linesum.inventory.domain.model.store.Goods;
import com.linesum.inventory.domain.model.store.SalesStore;

import java.math.BigDecimal;
import java.util.List;

/**
 * 库存规则
 */
public class StoreConfig {

    private List<Goods> goodsList;

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public StoreConfig(List<Goods> goodsList) {
        Preconditions.checkNotNull(goodsList, "goodsList is required");
        this.goodsList = goodsList;
    }

    /**
     * 销售库存占比
     */
    public static class SalesRatioConfig extends StoreConfig {

        private BigDecimal ratio;

        public SalesRatioConfig(List<Goods> goodsList, BigDecimal ratio) {
            super(goodsList);
            this.ratio = ratio;
        }

        public BigDecimal getRatio() {
            return ratio;
        }
    }

    /**
     * 锁库库存占比
     */
    public static class LockRatioConfig extends StoreConfig {

        private BigDecimal  ratio;

        public LockRatioConfig(List<Goods> goodsList, BigDecimal ratio) {
            super(goodsList);
            this.ratio = ratio;
        }

        public BigDecimal getRatio() {
            return ratio;
        }
    }


}
