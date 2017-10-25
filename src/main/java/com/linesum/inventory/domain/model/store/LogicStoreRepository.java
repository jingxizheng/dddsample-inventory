package com.linesum.inventory.domain.model.store;

public interface LogicStoreRepository {

    LogicStore find(LogicStore.LogicStoreId logicStoreId);

    void save(LogicStore store);
}
