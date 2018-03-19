package com.pay.data.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestRedis {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void contextLoads() {
    }

    @Test
    public void testRedis(){
        stringRedisTemplate.opsForValue().set("test","redis");

        System.out.println(stringRedisTemplate.opsForValue().get("test")+"------------------");
//        Assert.assertEquals("redis",stringRedisTemplate.opsForValue().get("test"));
    }
}
