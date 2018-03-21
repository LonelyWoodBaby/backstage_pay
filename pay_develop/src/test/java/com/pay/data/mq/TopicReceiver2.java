package com.pay.data.mq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "topic.message")
public class TopicReceiver2 {
    @RabbitHandler
    public void process(String hello) {
        System.out.println("TopicReceiver2  : " + hello);
    }
}
