package com.linesum.inventory.infrastructure.persistence.jpa;

import com.linesum.inventory.infrastructure.persistence.jpa.po.LogicStorePo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zhengjx on 2017/10/27.
 */
@Repository
public interface LogicStoreRepositoryJpa extends CrudRepository<LogicStorePo, Long> {
}
