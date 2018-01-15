package com.linesum.inventory.infrastructure.persistence.mapper;

import com.linesum.inventory.infrastructure.persistence.po.PhysicalStoreGoodsMiddlePo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by zhengjx on 2017/10/25.
 */
@Repository
public interface PhysicalStoreGoodsMiddleMapper extends Mapper<PhysicalStoreGoodsMiddlePo> {

    List<PhysicalStoreGoodsMiddlePo> findByPhysicalStoreId(Long physicalStoreId);

    void deleteByPhysicalStoreId(Long physicalStoreId);
}
