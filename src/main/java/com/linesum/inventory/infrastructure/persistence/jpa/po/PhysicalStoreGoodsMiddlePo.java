package com.linesum.inventory.infrastructure.persistence.jpa.po;

import javax.persistence.*;

/**
 * Created by zhengjx on 2017/10/25.
 */
@Entity
@Table(name = "physical_store_goods_middle")
public class PhysicalStoreGoodsMiddlePo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long physicalStoreId;

    private Long goodsId;

    private Integer qty;

    public PhysicalStoreGoodsMiddlePo() {
    }

    public PhysicalStoreGoodsMiddlePo(Long id, Long physicalStoreId, Long goodsId, Integer qty) {
        this.id = id;
        this.physicalStoreId = physicalStoreId;
        this.goodsId = goodsId;
        this.qty = qty;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPhysicalStoreId() {
        return physicalStoreId;
    }

    public void setPhysicalStoreId(Long physicalStoreId) {
        this.physicalStoreId = physicalStoreId;
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
