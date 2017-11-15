package com.linesum.inventory.domain.model.storeconfig;

import com.linesum.inventory.domain.model.store.Goods;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zhengjx on 2017/10/24.
 */
public class StoreConfigHandlerImpl {

    private final static Log LOGGER = LogFactory.getLog(StoreConfigHandlerImpl.class);

    /**
     * 销售库存占比
     */
    public static class SalesRatioConfigHandler extends StoreConfigHandlerImpl implements StoreConfigHandler {

        @Override
        public List<Goods> handleConfig(StoreConfig storeConfig, List<Goods> goodsListSeed) {
            if (!this.sameConfigAs(storeConfig)) {
                LOGGER.error("sales ratio config not match");
            } else {
                StoreConfig.SalesRatioConfig salesRatioConfig = (StoreConfig.SalesRatioConfig) storeConfig;
                BigDecimal ratio = salesRatioConfig.getRatio();
                goodsListSeed.forEach(goods -> goods.multiply(ratio));
            }
            return goodsListSeed;
        }

        @Override
        public boolean sameConfigAs(StoreConfig other) {
            return other instanceof StoreConfig.SalesRatioConfig;
        }
    }

    /* public class SalesOtherConfigHandler extends StoreConfigHandlerImpl implements StoreConfigHandler ... */

}
