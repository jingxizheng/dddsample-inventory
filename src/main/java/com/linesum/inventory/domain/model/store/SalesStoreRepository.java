package com.linesum.inventory.domain.model.store;

/**
 * Created by zhengjx on 2017/10/30.
 */
public interface SalesStoreRepository {

    SalesStore find(SalesStore.SalesStoreId salesStoreId);
}
