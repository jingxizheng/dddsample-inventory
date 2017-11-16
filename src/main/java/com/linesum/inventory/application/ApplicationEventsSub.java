package com.linesum.inventory.application;

import com.linesum.inventory.domain.model.order.OrderId;
import java.util.Date;

public interface ApplicationEventsSub {

    /**
     * 商品入库完成
     */
    void inStoreDone(OrderId orderId, Date acceptDate);

    /**
     * 商品出库完成
     */
    void outStoreDone(OrderId orderId, Date acceptDate);

}
