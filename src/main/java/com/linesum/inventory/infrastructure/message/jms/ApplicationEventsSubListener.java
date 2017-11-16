package com.linesum.inventory.infrastructure.message.jms;

import com.alibaba.fastjson.JSONObject;
import com.linesum.inventory.application.ApplicationEventsSub;
import com.linesum.inventory.domain.model.order.OrderId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationEventsSubListener {

    @Autowired
    private ApplicationEventsSub applicationEventsSub;

    @JmsListener(destination = "in_store_done")
    public void inStoreDone(String msg) {
        JSONObject jsonObject = JSONObject.parseObject(msg);

        applicationEventsSub.inStoreDone(
                new OrderId(Long.valueOf(jsonObject.getString("orderId"))),
                jsonObject.getDate("acceptDate")
        );
    }

    @JmsListener(destination = "out_store_done")
    public void outStoreDone(String msg) {
        JSONObject jsonObject = JSONObject.parseObject(msg);

        applicationEventsSub.outStoreDone(
                new OrderId(Long.valueOf(jsonObject.getString("orderId"))),
                jsonObject.getDate("acceptDate")
        );
    }

}
