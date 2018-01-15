package com.linesum.inventory.infrastructure.persistence.po;

import javax.persistence.*;

/**
 * Created by zhengjx on 2017/11/2.
 */
@Entity
@Table(name = "order_goods_middle")
public class OrderGoodsMiddlePo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private Long goodsId;

    private Integer qty;

    public OrderGoodsMiddlePo(Long id, Long orderId, Long goodsId, Integer qty) {
        this.id = id;
        this.orderId = orderId;
        this.goodsId = goodsId;
        this.qty = qty;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
}
