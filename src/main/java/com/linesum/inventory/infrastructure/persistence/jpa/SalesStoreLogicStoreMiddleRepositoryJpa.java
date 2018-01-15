package com.linesum.inventory.infrastructure.persistence.jpa;

import com.linesum.inventory.infrastructure.persistence.po.SalesStoreLogicStoreMiddlePo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhengjx on 2017/10/31.
 */
@Repository
public interface SalesStoreLogicStoreMiddleRepositoryJpa extends CrudRepository<SalesStoreLogicStoreMiddlePo, Long> {

    List<SalesStoreLogicStoreMiddlePo> findBySalesStoreId(Long salesStoreId);
}
