package com.linesum.inventory.infrastructure.persistence.mapper;

import com.linesum.inventory.infrastructure.BaseMapper;
import com.linesum.inventory.infrastructure.persistence.po.GoodsPo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhengjx on 2017/10/25.
 */
@Repository
public interface GoodsMapper extends BaseMapper<GoodsPo> {

    GoodsPo findFirstBySkuCode(String skuCode);

    List<GoodsPo> findBySkuCodeIn(List<String> skuCodeList);

    List<GoodsPo> findByIdIn(List<Long> idList);

}
