package com.linesum.inventory.infrastructure.persistence.jpa;

import com.linesum.inventory.infrastructure.persistence.jpa.po.PhysicalStoreGoodsMiddlePo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zhengjx on 2017/10/25.
 */
@Repository
public interface PhysicalStoreGoodsMiddleRepositoryJpa extends CrudRepository<PhysicalStoreGoodsMiddlePo, Long> {
}
