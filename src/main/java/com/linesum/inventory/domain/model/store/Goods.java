package com.linesum.inventory.domain.model.store;

import com.linesum.inventory.domain.shared.Entity;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * 商品
 */
public class Goods implements Entity<Goods> {

    private SkuCode skuCode;

    private Integer qty = 0;

    private BigDecimal price = BigDecimal.ZERO;

    public Goods(SkuCode skuCode, Integer qty, BigDecimal price) {
        this.skuCode = skuCode;
        this.qty = qty;
        this.price = price;
    }

    public void add(Integer addQuantity) {
        this.qty += addQuantity;
    }

    public void reduce(Integer reduceQuantity) {
        this.qty -= reduceQuantity;
    }

    public void multiply(BigDecimal ratio) {
        this.qty = new BigDecimal(this.qty).multiply(ratio)
                .setScale(0, BigDecimal.ROUND_FLOOR)
                .intValue();
    }

    @Override
    public boolean sameIdentityAs(Goods other) {
        return other != null && this.skuCode.sameValueAs(other.skuCode);
    }

    public SkuCode getSkuCode() {
        return skuCode;
    }

    public Integer getQty() {
        return qty;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
