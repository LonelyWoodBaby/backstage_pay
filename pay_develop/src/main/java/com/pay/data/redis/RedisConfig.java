package com.pay.data.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
//@Component
public class RedisConfig{
    private StringRedisTemplate template;

    @Autowired
    public RedisConfig(StringRedisTemplate template){
        this.template = template;
    }

    public void setKey(String key, String value){
        ValueOperations<String,String> ops = template.opsForValue();
    }

    public String getValue(String key){
        ValueOperations<String, String> ops = this.template.opsForValue();
        return ops.get(key);
    }
}
