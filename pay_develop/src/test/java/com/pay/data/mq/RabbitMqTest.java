package com.pay.data.mq;

import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RabbitMqTest {

    @Autowired
    private MessageSender messageSender;
    @Autowired
    private MessageSender2 messageSender2;

    @Ignore
    public void testHello1(){
        messageSender.send();
    }

    @Ignore
    public void testOneToMany(){
        for(int i=0;i<10;i++){
            messageSender.send("hellomsg1:"+i +" ");
            messageSender2.send("hellomsg2:"+i +" ");
            messageSender.sendTopic();
        }
    }

    @Test
    public void testTopic(){
        messageSender.sendTopic();
    }
}
