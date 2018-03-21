package com.pay.data.mq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "helloQueue")
public class MessageReceiver3 {
    @RabbitHandler
    public void process(String hello) {
        System.out.println("Receiver3  : " + hello);
    }

}
