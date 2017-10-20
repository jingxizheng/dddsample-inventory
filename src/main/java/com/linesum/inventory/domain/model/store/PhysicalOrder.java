package com.linesum.inventory.domain.model.store;

import java.util.Date;
import java.util.List;

/**
 * 物理库存订单
 */
public class PhysicalOrder extends AbstractOrder<PhysicalOrder> {


    public PhysicalOrder(OrderId orderId, PhysicalOrder acceptor, PhysicalOrder sender, List<Goods> goodsList, Date sendDate) {
        super(orderId, acceptor, sender, goodsList, sendDate);
    }
}
