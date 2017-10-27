package com.linesum.inventory.infrastructure.message.config;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhengjx on 2017/10/27.
 */
@Configuration
public class ProducerConfig {

    @Autowired
    private CommonMQProperties commonMQProperties;

    @Bean
    public DefaultMQProducer defaultMQProducerBean() {
        DefaultMQProducer producer = new DefaultMQProducer(commonMQProperties.getProducerGroup());
        producer.setNamesrvAddr(commonMQProperties.getNamesrvAddr());
        producer.setInstanceName(commonMQProperties.getInstanceName());
        producer.setVipChannelEnabled(false);
        return producer;
    }
}
