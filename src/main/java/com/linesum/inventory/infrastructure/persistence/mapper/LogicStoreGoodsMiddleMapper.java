package com.linesum.inventory.infrastructure.persistence.mapper;

import com.linesum.inventory.infrastructure.persistence.po.LogicStoreGoodsMiddlePo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * Created by zhengjx on 2017/10/25.
 */
@Repository
public interface LogicStoreGoodsMiddleMapper extends Mapper<LogicStoreGoodsMiddlePo>, MySqlMapper<LogicStoreGoodsMiddlePo> {

    List<LogicStoreGoodsMiddlePo> findByLogicStoreId(Long logicStoreId);

    void deleteByLogicStoreId(Long logicStoreId);

}
