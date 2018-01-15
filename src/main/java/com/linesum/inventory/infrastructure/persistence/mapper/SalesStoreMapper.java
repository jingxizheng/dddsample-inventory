package com.linesum.inventory.infrastructure.persistence.mapper;

import com.linesum.inventory.infrastructure.persistence.po.SalesStorePo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created by zhengjx on 2017/10/31.
 */
@Repository
public interface SalesStoreMapper extends Mapper<SalesStorePo> {
}
