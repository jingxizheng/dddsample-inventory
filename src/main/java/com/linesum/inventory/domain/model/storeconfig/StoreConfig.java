package com.linesum.inventory.domain.model.storeconfig;

import java.math.BigDecimal;

/**
 * 库存规则
 */
public class StoreConfig {

    /**
     * 销售库存占比
     */
    public static class SalesRatioConfig extends StoreConfig {

        private BigDecimal ratio;

        public SalesRatioConfig(BigDecimal ratio) {
            this.ratio = ratio;
        }

        public BigDecimal getRatio() {
            return ratio;
        }
    }

}
