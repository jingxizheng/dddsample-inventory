package com.linesum.inventory.infrastructure.persistence.repository;

import com.linesum.inventory.domain.model.order.Contact;
import com.linesum.inventory.domain.model.order.ContactId;
import com.linesum.inventory.domain.model.store.PhysicalStore;
import com.linesum.inventory.domain.model.store.PhysicalStoreRepository;
import com.linesum.inventory.domain.model.store.WarehouseId;
import com.linesum.inventory.domain.model.store.WarehouseInfo;
import com.linesum.inventory.infrastructure.persistence.jpa.*;
import com.linesum.inventory.infrastructure.persistence.jpa.po.ContactPo;
import com.linesum.inventory.infrastructure.persistence.jpa.po.PhysicalStorePo;
import com.linesum.inventory.infrastructure.persistence.jpa.po.WarehouseContactMiddlePo;
import com.linesum.inventory.infrastructure.persistence.jpa.po.WarehousePo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhengjx on 2017/10/25.
 */
public class PhysicalStoreRepositoryImpl implements PhysicalStoreRepository {

    @Autowired
    private ContactRepositoryJpa contactRepositoryJpa;

    @Autowired
    private WarehouseContactMiddleRepositoryJpa warehouseContactMiddleRepositoryJpa;

    @Autowired
    private GoodsRepositoryJpa goodsRepositoryJpa;

    @Autowired
    private PhysicalStoreGoodsMiddleRepositoryJpa physicalStoreGoodsMiddleRepositoryJpa;

    @Autowired
    private PhysicalStoreRepositoryJpa physicalStoreRepositoryJpa;

    @Autowired
    private WarehouseRepositoryJpa warehouseRepositoryJpa;

    @Override
    public PhysicalStore find(PhysicalStore.PhysicalStoreId physicalStoreId) {
        PhysicalStorePo physicalStorePo = physicalStoreRepositoryJpa.findOne(physicalStoreId.getId());
        Long warehouseId = physicalStorePo.getWarehouseId();
        WarehousePo warehousePo = warehouseRepositoryJpa.findOne(warehouseId);
        WarehouseContactMiddlePo warehouseContactMiddlePo = warehouseContactMiddleRepositoryJpa.findFirstByWarehouseId(warehouseId);
        Long contactId = warehouseContactMiddlePo.getContactId();
        ContactPo contactPo = contactRepositoryJpa.findOne(contactId);

        return new PhysicalStore(physicalStoreId,
                new WarehouseId(warehouseId),
                new WarehouseInfo(
                        new Contact(new ContactId(contactId), contactPo.getName(), contactPo.getAddress(), contactPo.getTelephone()),
                        warehousePo.getUsedCapacity(),
                        warehousePo.getTotalCapacity()),
                null);
    }

    @Override
    public void save(PhysicalStore store) {

    }
}
