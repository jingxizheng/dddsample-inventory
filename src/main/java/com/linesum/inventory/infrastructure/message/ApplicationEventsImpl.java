package com.linesum.inventory.infrastructure.message;

import com.linesum.inventory.application.ApplicationEvents;
import com.linesum.inventory.domain.model.order.OrderId;
import com.linesum.inventory.infrastructure.message.config.CommonMQProperties;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by zhengjx on 2017/10/27.
 */
@Service
public class ApplicationEventsImpl implements ApplicationEvents {

    @Autowired
    private CommonMQProperties commonMQProperties;

    @Autowired
    private DefaultMQProducer defaultMQProducer;

    @Override
    public void transferInPhysicalStore(OrderId orderId) {
        sendMsg("STORE_IN", orderId.idString(), orderId.idString());
    }

    @Override
    public void transferOutPhysicalStore(OrderId orderId) {
        sendMsg("STORE_OUT", orderId.idString(), orderId.idString());
    }

    private SendResult sendMsg(String tag, String key, String body) {
        try {
            defaultMQProducer.start();
            Message msg = new Message(
                    commonMQProperties.getTopic(),// topic
                    tag,// tag
                    key,// key
                    (body).getBytes());// body
            msg.setDelayTimeLevel(3);
            return defaultMQProducer.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            defaultMQProducer.shutdown();
        }
        throw new MQSendException("MQ send fail: tag[" + tag + "] key[" + key + "] body[" + body + "]");
    }

    public class MQSendException extends RuntimeException {
        public MQSendException(String message) {
            super(message);
        }
    }
}
