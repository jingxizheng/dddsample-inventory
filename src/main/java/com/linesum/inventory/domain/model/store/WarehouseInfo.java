package com.linesum.inventory.domain.model.store;

import com.google.common.base.Preconditions;
import com.linesum.inventory.domain.model.order.Contact;
import com.linesum.inventory.domain.shared.ValueObject;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 仓库信息
 */
public class WarehouseInfo implements ValueObject<WarehouseInfo> {

    private final static Log LOGGER = LogFactory.getLog(WarehouseInfo.class);

    private Contact contact;

    private Integer usedCapacity;

    private Integer totalCapacity;

    public WarehouseInfo(Contact contact, Integer usedCapacity, Integer totalCapacity) {
        Preconditions.checkNotNull(contact, "contact is required");
        Preconditions.checkNotNull(usedCapacity, "usedCapacity is required");
        Preconditions.checkNotNull(totalCapacity, "totalCapacity is required");
        this.contact = contact;
        this.usedCapacity = usedCapacity;
        this.totalCapacity = totalCapacity;
    }

    public Contact getContact() {
        return contact;
    }

    public boolean enoughTotalCapacity(Integer inspectedQty) {
        if (inspectedQty == null) {
            LOGGER.warn("inspectedQty should be required");
            inspectedQty = 0;
        }
        return Integer.sum(usedCapacity, inspectedQty) <= totalCapacity;
    }

    public boolean enoughUsedCapacity(Integer inspectedQty) {
        if (inspectedQty == null) {
            LOGGER.warn("inspectedQty should be required");
            inspectedQty = 0;
        }
        return inspectedQty <= usedCapacity;
    }

    @Override
    public boolean sameValueAs(WarehouseInfo other) {
        return other != null && new EqualsBuilder().
                append(this.contact, other.contact).
                append(this.usedCapacity, other.usedCapacity).
                append(this.totalCapacity, other.totalCapacity).
                isEquals();
    }
}
