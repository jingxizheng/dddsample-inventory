package com.linesum.inventory.domain.model.storeconfig;

import com.linesum.inventory.domain.model.store.Goods;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.assertj.core.util.Lists;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zhengjx on 2017/10/24.
 */
public class StoreConfigHandlerImpl {

    private final static Log LOGGER = LogFactory.getLog(StoreConfigHandlerImpl.class);

    private List<Goods> goodsListSeed;

    private List<StoreConfigHandler> storeConfigHandlerList = Lists.newArrayList(
            new SalesRatioConfigHandler()
            /* new SalesOtherConfigHandler()... */
    );

    public List<Goods> execute(List<StoreConfig> storeConfigList, List<Goods> goodsListSeed) {
        this.goodsListSeed = goodsListSeed;

        storeConfigList.forEach(config ->
                storeConfigHandlerList.stream()
                        .filter(handler -> handler.sameEventAs(config))
                        .findFirst() // return an optional
                        .ifPresent(targetHandler -> targetHandler.handleConfig(config)));

        return this.goodsListSeed;
    }

    /**
     * 销售库存占比
     */
    public class SalesRatioConfigHandler extends StoreConfigHandlerImpl implements StoreConfigHandler {

        @Override
        public void handleConfig(StoreConfig storeConfig) {
            if (!this.sameEventAs(storeConfig)) {
                LOGGER.error("sales ratio config not match");
            } else {
                StoreConfig.SalesRatioConfig salesRatioConfig = (StoreConfig.SalesRatioConfig) storeConfig;
                BigDecimal ratio = salesRatioConfig.getRatio();
                super.goodsListSeed.forEach(goods -> goods.multiply(ratio));
            }
        }

        @Override
        public boolean sameEventAs(StoreConfig other) {
            return other instanceof StoreConfig.SalesRatioConfig;
        }
    }

    /* public class SalesOtherConfigHandler extends StoreConfigHandlerImpl implements StoreConfigHandler ... */

}
