package com.linesum.inventory.infrastructure.persistence.mapper;

import com.linesum.inventory.infrastructure.persistence.po.OrderGoodsMiddlePo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by zhengjx on 2017/11/2.
 */
@Repository
public interface OrderGoodsMiddleMapper extends Mapper<OrderGoodsMiddlePo> {

    List<OrderGoodsMiddlePo> findByOrderId(Long orderId);
}
