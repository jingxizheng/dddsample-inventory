package com.linesum.inventory.infrastructure.persistence.po;

import javax.persistence.*;

/**
 * Created by zhengjx on 2017/10/31.
 */
@Entity
@Table(name = "sales_store_physical_store_middle")
public class SalesStoreLogicStoreMiddlePo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long salesStoreId;

    private Long logicStoreId;

    public SalesStoreLogicStoreMiddlePo(Long id, Long salesStoreId, Long logicStoreId) {
        this.id = id;
        this.salesStoreId = salesStoreId;
        this.logicStoreId = logicStoreId;
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

    public Long getLogicStoreId() {
        return logicStoreId;
    }

    public void setLogicStoreId(Long logicStoreId) {
        this.logicStoreId = logicStoreId;
    }
}
