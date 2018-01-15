package com.linesum.inventory.domain.repository;

import com.linesum.inventory.domain.model.store.LogicStore;

public interface LogicStoreRepository {

    LogicStore find(LogicStore.LogicStoreId logicStoreId);

    LogicStore.LogicStoreId save(LogicStore store);
}
