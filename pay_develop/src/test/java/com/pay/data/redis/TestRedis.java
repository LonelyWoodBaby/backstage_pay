package com.pay.data.redis;

import com.pay.beans.dictionary.DictionaryConfig;
import com.pay.schedule.pojo.model.ScheduleExecutionRecord;
import com.pay.schedule.service.ScheduleExecutionRecordService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestRedis {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ScheduleExecutionRecordService scheduleExecutionRecordService;
    @Test
    public void contextLoads() {
    }

    @Test
    public void testRedis(){
        stringRedisTemplate.opsForValue().set("test","redis");
        System.out.println(stringRedisTemplate.opsForValue().get("test")+"------------------");
//        Assert.assertEquals("redis",stringRedisTemplate.opsForValue().get("test"));
    }

    @Test
    public void testReadAllRecord(){
        List<ScheduleExecutionRecord> recordList = scheduleExecutionRecordService.getAllExecutionList();
        System.out.println("第一次查询");
        List<ScheduleExecutionRecord> recordList2 = scheduleExecutionRecordService.getAllExecutionList();
        System.out.println("第二次查询");
        List<ScheduleExecutionRecord> recordList3 = scheduleExecutionRecordService.getAllExecutionList();
        System.out.println("第三次查询");
    }

    @Test
    public void testBeanDictionarySize(){
        System.out.println("valueSize:"+DictionaryConfig.valueObjectDictionaryCache.size());
        System.out.println("dataSize:"+DictionaryConfig.dataObjectDictionaryCache.size());
        System.out.println("allSize:"+DictionaryConfig.allObjectDictionaryCache.size());
    }
}
