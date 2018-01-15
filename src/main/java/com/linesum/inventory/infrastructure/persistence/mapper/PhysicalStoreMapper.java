package com.linesum.inventory.infrastructure.persistence.mapper;

import com.linesum.inventory.infrastructure.persistence.po.PhysicalStorePo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created by zhengjx on 2017/10/25.
 */
@Repository
public interface PhysicalStoreMapper extends Mapper<PhysicalStorePo> {
}
