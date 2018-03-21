package com.pay.data.mq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "topic.messages")
public class TopicReceiver {
    @RabbitHandler
    public void process(String hello) {
        System.out.println("TopicReceiver  : " + hello);
    }
}
