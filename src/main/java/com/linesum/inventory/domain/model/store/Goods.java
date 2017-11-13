package com.linesum.inventory.domain.model.store;

import com.linesum.inventory.domain.shared.Entity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goods goods = (Goods) o;
        return Objects.equals(skuCode, goods.skuCode) &&
                Objects.equals(qty, goods.qty) &&
                Objects.equals(price, goods.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skuCode, qty, price);
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
