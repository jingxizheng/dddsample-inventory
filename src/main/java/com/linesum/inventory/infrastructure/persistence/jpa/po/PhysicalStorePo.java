package com.linesum.inventory.infrastructure.persistence.jpa.po;

import javax.persistence.*;
import java.util.List;

/**
 * Created by zhengjx on 2017/10/25.
 */
@Entity
@Table(name = "physical_store")
public class PhysicalStorePo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long warehouseId;

    public PhysicalStorePo() {
    }

    public PhysicalStorePo(Long id, Long warehouseId) {
        this.id = id;
        this.warehouseId = warehouseId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }
}
