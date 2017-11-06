package com.linesum.inventory.domain.model.storeconfig;

import com.google.common.collect.Lists;
import com.linesum.inventory.domain.model.store.Goods;

import java.util.List;
import java.util.Optional;

/**
 * Created by zhengjx on 2017/11/6.
 */
public class StoreConfigExecutor {


    List<StoreConfigHandler> storeConfigHandlerList = Lists.newArrayList(
            new StoreConfigHandlerImpl.SalesRatioConfigHandler()
            /* new SalesOtherConfigHandler()... */
    );

    public List<Goods> execute(List<StoreConfig> storeConfigList, List<Goods> goodsListSeed) {
        for (StoreConfig storeConfig : storeConfigList) {

            Optional<StoreConfigHandler> storeConfigHandlerOptional = storeConfigHandlerList.stream()
                    .filter(handler -> handler.sameEventAs(storeConfig))
                    .findFirst();// return an optional

            if (storeConfigHandlerOptional.isPresent()) {
                goodsListSeed = storeConfigHandlerOptional.get().handleConfig(storeConfig, goodsListSeed);
            }
        }
        return goodsListSeed;
    }
}
