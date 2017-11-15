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

    private Date acceptDate; // 收货日期

    public Order(OrderId orderId, Contact acceptor, Contact sender, List<Goods> goodsList, Date sendDate) {
        this.orderId = orderId;
        this.acceptor = acceptor;
        this.sender = sender;
        this.orderGoodsList = goodsList;
        this.sendDate = sendDate;
    }

    public void accept(final Date date) {
        this.acceptDate = date;
    }

    @Override
    public boolean sameIdentityAs(Order other) {
        return other != null && this.orderId.sameValueAs(other.orderId);
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public Contact getAcceptor() {
        return acceptor;
    }

    public Contact getSender() {
        return sender;
    }

    public List<Goods> getOrderGoodsList() {
        return orderGoodsList;
    }

    public Date getSendDate() {
        return (Date) sendDate.clone();
    }

    public Date getAcceptDate() {
        return (Date) acceptDate.clone();
    }
}
