package com.linesum.inventory.application;

import com.linesum.inventory.domain.model.store.PhysicalStore;

/**
 * 应用事件 这个接口提供了一种方式让系统的其他部分知道发生乐什么事件
 */
public interface ApplicationEvents {

    /**
     * 商品入库
     */
    void transferInPhysicalStore(PhysicalStore physicalStore);

    /**
     * 商品出库
     */
    void transferOutPhysicalStore(PhysicalStore physicalStore);
}
