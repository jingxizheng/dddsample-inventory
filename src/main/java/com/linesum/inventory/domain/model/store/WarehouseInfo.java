package com.linesum.inventory.domain.model.store;

import com.google.common.base.Preconditions;
import com.linesum.inventory.domain.model.order.Contact;
import com.linesum.inventory.domain.shared.ValueObject;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 仓库信息
 * 此处仓库信息作为一个值对象是一个糟糕的实践，它违背了不变性，概念整体，值对象创建后应该是不予许被更改的。
 *
 * INFO 一个值对象可以只处理单个属性，也可以处理一组相关联的属性。
 * 在这组相关联的属性中，每一个属性都是整体属性所不可或缺的组成部分。
 * 如果一组属性联合起来并不能表达一个整体上的概念，那么这种联合并无多大用处，体现概念整体。
 */
public class WarehouseInfo implements ValueObject<WarehouseInfo> {

    private final static Log LOGGER = LogFactory.getLog(WarehouseInfo.class);

    /*
     * INFO 在值对象中存在实体的引用，保持不变性、表达性和方便性，需要谨慎使用避免实体对象违背值对象的不变性
     */
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

    public void addUsedCapacity(Integer qty) {
        this.usedCapacity = usedCapacity + qty;
    }

    public void reduceUsedCapacity(Integer qty) {
        this.usedCapacity = usedCapacity - qty;
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

    public Contact getContact() {
        return contact;
    }

    public Integer getUsedCapacity() {
        return usedCapacity;
    }

    public Integer getTotalCapacity() {
        return totalCapacity;
    }
}
