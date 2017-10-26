package com.linesum.inventory.infrastructure.persistence.jpa.po;

import javax.persistence.*;

/**
 * Created by zhengjx on 2017/10/25.
 */
@Entity
@Table(name = "warehouse_contact_middle")
public class WarehouseContactMiddlePo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long contactId;

    private Long warehouseId;

    public WarehouseContactMiddlePo() {
    }

    public WarehouseContactMiddlePo(Long id, Long contactId, Long warehouseId) {
        this.id = id;
        this.contactId = contactId;
        this.warehouseId = warehouseId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }
}
