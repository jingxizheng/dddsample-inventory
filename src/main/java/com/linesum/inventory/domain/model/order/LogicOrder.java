package com.linesum.inventory.domain.model.order;

import com.linesum.inventory.domain.model.store.Goods;

import java.util.Date;
import java.util.List;

/**
 * 逻辑库存订单
 */
public class LogicOrder extends AbstractOrder {

    public LogicOrder(OrderId orderId, Contact acceptor, Contact sender, List<Goods> goodsList, Date sendDate) {
        super(orderId, acceptor, sender, goodsList, sendDate);
    }
}
