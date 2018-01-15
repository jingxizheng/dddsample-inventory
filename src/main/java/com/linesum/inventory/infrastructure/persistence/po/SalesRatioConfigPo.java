package com.linesum.inventory.infrastructure.persistence.po;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by zhengjx on 2017/10/31.
 */
@Entity
@Table(name = "sales_ratio_config")
public class SalesRatioConfigPo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long salesStoreId;

    private BigDecimal ratio;

    public SalesRatioConfigPo(Long id, Long salesStoreId, BigDecimal ratio) {
        this.id = id;
        this.salesStoreId = salesStoreId;
        this.ratio = ratio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSalesStoreId() {
        return salesStoreId;
    }

    public void setSalesStoreId(Long salesStoreId) {
        this.salesStoreId = salesStoreId;
    }

    public BigDecimal getRatio() {
        return ratio;
    }

    public void setRatio(BigDecimal ratio) {
        this.ratio = ratio;
    }
}
