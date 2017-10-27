package com.linesum.inventory.domain.model.order;

import com.google.common.base.Preconditions;
import com.linesum.inventory.domain.model.store.Goods;
import com.linesum.inventory.domain.shared.Entity;

import java.util.Date;
import java.util.List;

/**
 * 抽象订单
 *
 */
public class Order implements Entity<Order> {

    private OrderId orderId; // 订单ID

    private Contact acceptor; // 接收方

    private Contact sender; // 发送方

    private List<Goods> orderGoodsList; // 商品列表

    private Date sendDate; // 发货日期

    private Date acceptorDate; // 收货日期

    public Order(OrderId orderId, Contact acceptor, Contact sender, List<Goods> goodsList, Date sendDate) {
        Preconditions.checkNotNull(acceptor, "acceptor is required");
        Preconditions.checkNotNull(orderId, "orderId is required");
        Preconditions.checkNotNull(sender, "sender is required");
        Preconditions.checkNotNull(goodsList, "orderGoodsList is required");
        Preconditions.checkNotNull(sendDate, "sendDate is required");
        this.orderId = orderId;
        this.acceptor = acceptor;
        this.sender = sender;
        this.orderGoodsList = goodsList;
        this.sendDate = sendDate;
    }

    public Order addOrderId(OrderId orderId) {
        this.orderId = orderId;
        return this;
    }

    @Override
    public boolean sameIdentityAs(Order other) {
        return other != null && this.orderId.sameValueAs(other.orderId);
    }
}