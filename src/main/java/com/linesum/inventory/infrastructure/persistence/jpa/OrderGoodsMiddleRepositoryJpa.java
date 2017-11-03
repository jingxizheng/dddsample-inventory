package com.linesum.inventory.infrastructure.persistence.jpa;

import com.linesum.inventory.infrastructure.persistence.jpa.po.OrderGoodsMiddlePo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhengjx on 2017/11/2.
 */
@Repository
public interface OrderGoodsMiddleRepositoryJpa extends CrudRepository<OrderGoodsMiddlePo, Long> {

    List<OrderGoodsMiddlePo> findByOrderId(Long orderId);
}
