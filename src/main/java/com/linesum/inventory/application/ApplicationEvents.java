package com.linesum.inventory.application;

import com.linesum.inventory.domain.model.store.PhysicalStore;

/**
 * 应用事件
 *
 * 告知其他服务(如物流服务)商品入库或者出库了 需要发货或者收货
 * 可以在基础设施层 基于MQ实现或者RPC实现
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
