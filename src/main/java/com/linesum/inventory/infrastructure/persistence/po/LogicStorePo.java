package com.linesum.inventory.infrastructure.persistence.po;

import javax.persistence.*;

/**
 * Created by zhengjx on 2017/10/27.
 */
@Entity
@Table(name = "logic_store")
public class LogicStorePo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long physicalStoreId;

    public LogicStorePo(Long id, Long physicalStoreId) {
        this.id = id;
        this.physicalStoreId = physicalStoreId;
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
}
