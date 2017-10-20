package com.linesum.inventory.domain.model.store;

import com.linesum.inventory.domain.shared.Entity;

import java.math.BigDecimal;

/**
 * 商品
 */
public class Goods implements Entity<Goods> {

    private SkuCode skuCode;

    private Integer qty = 0;

    private BigDecimal price = BigDecimal.ZERO;

    public Integer getQty() {
        return qty;
    }

    public void add(Integer addQuantity) {
        this.qty += addQuantity;
    }

    public void reduce(Integer reduceQuantity) {
        this.qty -= reduceQuantity;
    }

    @Override
    public boolean sameIdentityAs(Goods other) {
        return other != null && this.skuCode.sameValueAs(other.skuCode);
    }
}
