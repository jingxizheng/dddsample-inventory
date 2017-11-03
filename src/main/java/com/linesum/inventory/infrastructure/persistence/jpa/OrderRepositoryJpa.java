package com.linesum.inventory.infrastructure.persistence.jpa;

import com.linesum.inventory.infrastructure.persistence.jpa.po.OrderPo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zhengjx on 2017/11/2.
 */
@Repository
public interface OrderRepositoryJpa extends CrudRepository<OrderPo, Long> {
}
