package com.linesum.inventory.infrastructure.persistence.repository;

import com.linesum.inventory.domain.model.order.Contact;
import com.linesum.inventory.domain.model.order.ContactId;
import com.linesum.inventory.domain.model.store.*;
import com.linesum.inventory.domain.repository.PhysicalStoreRepository;
import com.linesum.inventory.infrastructure.persistence.jpa.*;
import com.linesum.inventory.infrastructure.persistence.po.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by zhengjx on 2017/10/25.
 */
@Repository
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
    @Transactional(rollbackFor = Exception.class)
    public PhysicalStore find(PhysicalStore.PhysicalStoreId physicalStoreId) {
        PhysicalStorePo physicalStorePo = physicalStoreRepositoryJpa.findOne(physicalStoreId.getId());
        Long warehouseId = physicalStorePo.getWarehouseId();
        WarehousePo warehousePo = warehouseRepositoryJpa.findOne(warehouseId);
        WarehouseContactMiddlePo warehouseContactMiddlePo = warehouseContactMiddleRepositoryJpa.findFirstByWarehouseId(warehouseId);
        Long contactId = warehouseContactMiddlePo.getContactId();
        ContactPo contactPo = contactRepositoryJpa.findOne(contactId);

        List<PhysicalStoreGoodsMiddlePo> physicalStoreGoodsMiddlePoList = physicalStoreGoodsMiddleRepositoryJpa.findByPhysicalStoreId(physicalStoreId.getId());
        List<Long> goodsIdList = physicalStoreGoodsMiddlePoList.stream()
                .map(PhysicalStoreGoodsMiddlePo::getGoodsId)
                .collect(Collectors.toList());
        List<GoodsPo> goodsPoList = goodsRepositoryJpa.findByIdIn(goodsIdList);

        List<Goods> goodsList = physicalStoreGoodsMiddlePoList.stream()
                .map(psgmPo -> {
                    GoodsPo goodsPo = goodsPoList.stream()
                            .filter(gPo -> Objects.equals(psgmPo.getGoodsId(), gPo.getId()))
                            .findFirst()
                            .get();
                    return new Goods(new SkuCode(goodsPo.getSkuCode()), psgmPo.getQty(), goodsPo.getPrice());
                })
                .collect(Collectors.toList());

        return new PhysicalStore(physicalStoreId,
                new WarehouseId(warehouseId),
                new WarehouseInfo(
                        new Contact(new ContactId(contactId), contactPo.getName(), contactPo.getAddress(), contactPo.getTelephone()),
                        warehousePo.getUsedCapacity(),
                        warehousePo.getTotalCapacity()),
                goodsList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PhysicalStore.PhysicalStoreId save(PhysicalStore physicalStore) {
        Long physicalStoreId = physicalStore.getPhysicalStoreId().getId();
        Long warehouseId = physicalStore.getWarehouseId().getId();
        WarehouseInfo warehouseInfo = physicalStore.getWarehouseInfo();
        Contact contact = warehouseInfo.getContact();
        Long contactId = contact.getContactId().getId();
        List<Goods> goodsList = physicalStore.getGoodsList();


        ContactPo contactPo = contactRepositoryJpa.save(new ContactPo(contactId, contact.getName(), contact.getAddress(), contact.getTelephone()));
        WarehousePo warehousePo = warehouseRepositoryJpa.save(new WarehousePo(warehouseId, warehouseInfo.getUsedCapacity(), warehouseInfo.getTotalCapacity()));
        WarehouseContactMiddlePo warehouseContactMiddlePo = warehouseContactMiddleRepositoryJpa.findFirstByWarehouseIdAndContactId(warehousePo.getId(), contactPo.getId());

        warehouseContactMiddleRepositoryJpa.save(new WarehouseContactMiddlePo(
                warehouseContactMiddlePo == null ? null : warehouseContactMiddlePo.getId(),
                contactPo.getId(),
                warehousePo.getId()));

        PhysicalStorePo physicalStorePo = physicalStoreRepositoryJpa.save(new PhysicalStorePo(physicalStoreId, warehousePo.getId()));
        physicalStoreGoodsMiddleRepositoryJpa.deleteByPhysicalStoreId(physicalStorePo.getId());

        List<PhysicalStoreGoodsMiddlePo> physicalStoreGoodsMiddlePoList = goodsList.stream()
                .map(goods -> new PhysicalStoreGoodsMiddlePo(null,
                        physicalStorePo.getId(),
                        goodsRepositoryJpa.findFirstBySkuCode(goods.getSkuCode().getCode()).getId(),
                        goods.getQty()))
                .collect(Collectors.toList());

        physicalStoreGoodsMiddleRepositoryJpa.save(physicalStoreGoodsMiddlePoList);
        return new PhysicalStore.PhysicalStoreId(physicalStorePo.getId());
    }
}
