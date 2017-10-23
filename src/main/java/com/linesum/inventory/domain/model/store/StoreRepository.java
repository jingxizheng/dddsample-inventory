package com.linesum.inventory.domain.model.store;

import java.util.List;

public interface StoreRepository<S extends AbstractStore> {

    S find(WarehouseId warehouseId);

    List<S> findAll();

    void save(S store);

    WarehouseId nextWarehouseId();
}
