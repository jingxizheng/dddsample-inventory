package com.linesum.inventory.infrastructure.persistence.po;

import javax.persistence.*;

/**
 * Created by zhengjx on 2017/10/25.
 */
@Entity
@Table(name = "logic_store_goods_middle")
public class LogicStoreGoodsMiddlePo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long logicStoreId;

    private Long goodsId;

    private Integer qty;

    public LogicStoreGoodsMiddlePo() {
    }

    public LogicStoreGoodsMiddlePo(Long id, Long logicStoreId, Long goodsId, Integer qty) {
        this.id = id;
        this.logicStoreId = logicStoreId;
        this.goodsId = goodsId;
        this.qty = qty;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLogicStoreId() {
        return logicStoreId;
    }

    public void setLogicStoreId(Long logicStoreId) {
        this.logicStoreId = logicStoreId;
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
