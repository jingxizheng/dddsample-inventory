package com.linesum.inventory.infrastructure.persistence.mapper;

import com.linesum.inventory.infrastructure.persistence.po.SalesRatioConfigPo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created by zhengjx on 2017/10/31.
 */
@Repository
public interface SalesRatioConfigMapper extends Mapper<SalesRatioConfigPo> {

    SalesRatioConfigPo findFirstBySalesStoreId(Long salesStoreId);
}
