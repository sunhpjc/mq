package com.sunhp.activiti.service.impl;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author sunhp
 * @Description 测试Redis操作
 * @Date 2020/4/15 15:46
 **/
@Service
public class RedisService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    /*
    * 设置值
    * */
    public void setString(String key,String value){
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(key, value);
    }
    public String getString(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }
}
