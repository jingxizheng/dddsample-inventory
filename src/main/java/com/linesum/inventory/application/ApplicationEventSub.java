package com.linesum.inventory.application;

import com.linesum.inventory.domain.model.order.OrderId;
import java.util.Date;

public interface ApplicationEventSub {

    /**
     * 商品入库完成
     */
    OrderId inStoreDone(OrderId orderId, Date acceptDate);

    /**
     * 商品出库完成
     */
    OrderId outStoreDone(OrderId orderId, Date acceptDate);

}
