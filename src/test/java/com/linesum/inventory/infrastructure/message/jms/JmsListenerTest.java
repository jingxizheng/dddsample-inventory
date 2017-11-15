package com.linesum.inventory.infrastructure.message.jms;

import org.assertj.core.api.Assertions;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class JmsListenerTest {

    @JmsListener(destination = "destinationTest")
    public void receive(String msg){
        System.out.println("------------------------------------------");
        System.out.println("------------------------------------------");
        System.out.println("------------------------------------------");
        System.out.println("---------------------receive message: " + msg);
        System.out.println("------------------------------------------");
        System.out.println("------------------------------------------");
        System.out.println("------------------------------------------");
        Assertions.assertThat(msg).isEqualTo("messageTest");
    }
}
