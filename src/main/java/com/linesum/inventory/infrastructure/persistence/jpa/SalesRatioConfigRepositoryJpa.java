package com.linesum.inventory.infrastructure.persistence.jpa;

import com.linesum.inventory.infrastructure.persistence.po.SalesRatioConfigPo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zhengjx on 2017/10/31.
 */
@Repository
public interface SalesRatioConfigRepositoryJpa extends CrudRepository<SalesRatioConfigPo, Long> {

    SalesRatioConfigPo findFirstBySalesStoreId(Long salesStoreId);
}
