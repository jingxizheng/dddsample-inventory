package com.linesum.inventory.infrastructure;

import com.linesum.inventory.infrastructure.persistence.po.GoodsPo;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface BaseMapper<T> extends Mapper<GoodsPo>,
        MySqlMapper<T>,
        IdsMapper<T>,
        ConditionMapper<T> {
}
