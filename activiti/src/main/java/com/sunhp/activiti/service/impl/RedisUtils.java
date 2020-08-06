package com.sunhp.activiti.service.impl;

import com.sunhp.activiti.enums.ResponseCodeEnum;
import com.sunhp.activiti.exception.MyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/**
 * @author sunhp
 * @Description 仅做学习使用，单独一个工具类
 * @Date 2020/4/20 16:28
 **/
public class RedisUtils {
    private final Logger logger = LoggerFactory.getLogger(RedisService.class);

    @Resource
    private JedisPool jedisPool;

    /*
    * 获取Jedis实例
    * */
    public synchronized Jedis getJedis(){
        try{
            if(jedisPool != null){
                return jedisPool.getResource();
            }else {
                logger.error("===获取Jedis实例失败===");
                throw new MyException(ResponseCodeEnum.FAILED);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("===获取jedis实例失败 ==> {}===",e.getMessage());
            //只是示例，实际会定义其它异常码
            throw new MyException(ResponseCodeEnum.FAILED);
        }
    }
    /*
    * 释放Jedis资源
    * */
    public void closeJedis(final Jedis jedis){
        if(jedis != null && jedis.isConnected()){
            jedis.close();
        }
    }
    /*
    * 加锁，内部使用
    * */
    public String addLockInner(Jedis jedis,String key){
        return null;
    }
}
