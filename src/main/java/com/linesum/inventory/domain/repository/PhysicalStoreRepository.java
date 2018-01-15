package com.linesum.inventory.domain.repository;

import com.linesum.inventory.domain.model.store.PhysicalStore;

public interface PhysicalStoreRepository {

    PhysicalStore find(PhysicalStore.PhysicalStoreId physicalStoreId);

    PhysicalStore.PhysicalStoreId save(PhysicalStore store);
}
