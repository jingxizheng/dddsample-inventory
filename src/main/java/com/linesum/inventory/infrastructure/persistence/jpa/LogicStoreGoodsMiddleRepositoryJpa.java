package com.linesum.inventory.infrastructure.persistence.jpa;

import com.linesum.inventory.infrastructure.persistence.po.LogicStoreGoodsMiddlePo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhengjx on 2017/10/25.
 */
@Repository
public interface LogicStoreGoodsMiddleRepositoryJpa extends CrudRepository<LogicStoreGoodsMiddlePo, Long> {

    List<LogicStoreGoodsMiddlePo> findByLogicStoreId(Long logicStoreId);

    void deleteByLogicStoreId(Long logicStoreId);
}
