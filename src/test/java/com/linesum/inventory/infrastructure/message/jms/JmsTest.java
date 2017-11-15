package com.linesum.inventory.infrastructure.message.jms;

import com.linesum.inventory.BaseJunitTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

public class JmsTest extends BaseJunitTestCase {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Test
    public void test() throws Exception {
        jmsTemplate.convertAndSend("destinationTest", "messageTest");
        Thread.sleep(1000);
    }

}