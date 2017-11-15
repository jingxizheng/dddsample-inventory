package com.linesum.inventory.application;

import com.linesum.inventory.domain.model.order.OrderId;

public interface ApplicationEventsPub {

    /**
     * 商品入库
     */
    void inStoreStart(OrderId orderId);

    /**
     * 商品出库
     */
    void outStoreStart(OrderId orderId);

}
