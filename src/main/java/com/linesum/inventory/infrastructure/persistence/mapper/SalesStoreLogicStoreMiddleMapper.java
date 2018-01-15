package com.linesum.inventory.infrastructure.persistence.mapper;

import com.linesum.inventory.infrastructure.persistence.po.SalesStoreLogicStoreMiddlePo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by zhengjx on 2017/10/31.
 */
@Repository
public interface SalesStoreLogicStoreMiddleMapper extends Mapper<SalesStoreLogicStoreMiddlePo> {

    List<SalesStoreLogicStoreMiddlePo> findBySalesStoreId(Long salesStoreId);
}
