package com.linesum.inventory.infrastructure.persistence.jpa;

import com.linesum.inventory.infrastructure.persistence.jpa.po.PhysicalStorePo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zhengjx on 2017/10/25.
 */
@Repository
public interface PhysicalStoreRepositoryJpa extends CrudRepository<PhysicalStorePo, Long> {
}
