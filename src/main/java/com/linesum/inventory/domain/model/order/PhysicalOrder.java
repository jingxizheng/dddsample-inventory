package com.linesum.inventory.domain.model.order;

import com.linesum.inventory.domain.model.logistics.Logistics;
import com.linesum.inventory.domain.model.store.Goods;

import java.util.Date;
import java.util.List;

/**
 * 物理库存订单
 */
public class PhysicalOrder extends AbstractOrder {

    private Logistics logistics;


    public PhysicalOrder(OrderId orderId, Contact acceptor, Contact sender, List<Goods> goodsList, Date sendDate) {
        super(orderId, acceptor, sender, goodsList, sendDate);
    }
}
