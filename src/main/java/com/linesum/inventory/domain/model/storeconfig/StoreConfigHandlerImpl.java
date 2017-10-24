package com.linesum.inventory.domain.model.storeconfig;

import com.linesum.inventory.domain.model.store.Goods;
import com.linesum.inventory.domain.model.store.SalesStore;
import com.linesum.inventory.domain.model.store.WarehouseInfo;
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
    public class SalesRatioConfigHandler implements StoreConfigHandler {

        @Override
        public void handleConfig(StoreConfig storeConfig) {
            if (!this.sameEventAs(storeConfig)) {
                LOGGER.error("sales ratio config not match");
            } else {
                StoreConfig.SalesRatioConfig salesRatioConfig = (StoreConfig.SalesRatioConfig) storeConfig;
                List<Goods> goodsList = salesRatioConfig.getGoodsList();
                BigDecimal ratio = salesRatioConfig.getRatio();
                goodsList.forEach(goods -> goods.multiply(ratio));
            }
        }

        @Override
        public boolean sameEventAs(StoreConfig other) {
            return other instanceof StoreConfig.SalesRatioConfig;
        }
    }

    /**
     * 锁库库存占比
     */
    public class LockRatioConfigHandler implements StoreConfigHandler {

        @Override
        public void handleConfig(StoreConfig storeConfig) {
            if (!this.sameEventAs(storeConfig)) {
                LOGGER.error("lock ratio config not match");
            } else {
                StoreConfig.LockRatioConfig lockRatioConfig = (StoreConfig.LockRatioConfig) storeConfig;
                List<Goods> goodsList = lockRatioConfig.getGoodsList();
                BigDecimal ratio = lockRatioConfig.getRatio();
                goodsList.forEach(goods -> goods.multiply(ratio));
            }
        }

        @Override
        public boolean sameEventAs(StoreConfig other) {
            return other instanceof StoreConfig.LockRatioConfig;
        }
    }
}
