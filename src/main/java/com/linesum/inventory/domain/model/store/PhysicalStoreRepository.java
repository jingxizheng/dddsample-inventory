package com.linesum.inventory.domain.model.store;

public interface PhysicalStoreRepository {

    PhysicalStore find(PhysicalStore.PhysicalStoreId physicalStoreId);

    PhysicalStore.PhysicalStoreId save(PhysicalStore store);
}
