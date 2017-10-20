package com.linesum.inventory.domain.model.store;

import java.util.Date;
import java.util.List;

/**
 * 逻辑库存订单
 */
public class LogicOrder extends AbstractOrder<LogicOrder> {


    public LogicOrder(OrderId orderId, LogicOrder acceptor, LogicOrder sender, List<Goods> goodsList, Date sendDate) {
        super(orderId, acceptor, sender, goodsList, sendDate);
    }
}
