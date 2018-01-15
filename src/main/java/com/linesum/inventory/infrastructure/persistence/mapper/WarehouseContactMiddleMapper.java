package com.linesum.inventory.infrastructure.persistence.mapper;

import com.linesum.inventory.infrastructure.persistence.po.WarehouseContactMiddlePo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created by zhengjx on 2017/10/25.
 */
@Repository
public interface WarehouseContactMiddleMapper extends Mapper<WarehouseContactMiddlePo> {

    WarehouseContactMiddlePo findFirstByWarehouseId(Long warehouseId);

    WarehouseContactMiddlePo findFirstByWarehouseIdAndContactId(Long warehouseId, Long contactId);
}
