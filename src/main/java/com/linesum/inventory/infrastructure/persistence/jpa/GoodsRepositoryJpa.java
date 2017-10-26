package com.linesum.inventory.infrastructure.persistence.jpa;

import com.linesum.inventory.infrastructure.persistence.jpa.po.GoodsPo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhengjx on 2017/10/25.
 */
@Repository
public interface GoodsRepositoryJpa extends CrudRepository<GoodsPo, Long> {

    GoodsPo findFirstBySkuCode(String skuCode);

    List<GoodsPo> findBySkuCodeIn(List<String> skuCodeList);

    List<GoodsPo> findByIdIn(List<Long> idList);

}
