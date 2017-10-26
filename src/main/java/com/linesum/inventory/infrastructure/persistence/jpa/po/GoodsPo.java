package com.linesum.inventory.infrastructure.persistence.jpa.po;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by zhengjx on 2017/10/25.
 */
@Entity
@Table(name = "goods")
public class GoodsPo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String skuCode;

    private BigDecimal price;

    public GoodsPo() {
    }

    public GoodsPo(Long id, String skuCode, BigDecimal price) {
        this.id = id;
        this.skuCode = skuCode;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
