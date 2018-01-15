package com.linesum.inventory.domain.repository;

import com.linesum.inventory.domain.model.store.SalesStore;

/**
 * Created by zhengjx on 2017/10/30.
 */
public interface SalesStoreRepository {

    SalesStore find(SalesStore.SalesStoreId salesStoreId);

    SalesStore.SalesStoreId save(SalesStore salesStore);
}
