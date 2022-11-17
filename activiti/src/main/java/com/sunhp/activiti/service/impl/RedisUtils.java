package com.sunhp.activiti.service.impl;

import com.sunhp.activiti.enums.ResponseCodeEnum;
import com.sunhp.activiti.exception.MyException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/**
 * @author sunhp
 * @Description 仅做学习使用，单独一个工具类
 * @Date 2020/4/20 16:28
 **/
@Component
public class RedisUtils {
    private final Logger logger = LoggerFactory.getLogger(RedisService.class);

    @Resource
    private JedisPool jedisPool;
    @Value("${spring.redis.cache.prefix}")
    private String cachePrefix;

    /**
     * 获取jedis连接
     *
     * @return
     */
    public synchronized Jedis getJedis() {
        try {
            if (jedisPool != null) {
                return jedisPool.getResource();
            } else {
                logger.error("===获取Jedis实例失败===");
                throw new MyException(ResponseCodeEnum.REDIS_JEDIS_POOL_ERROR);
            }
        } catch (Exception e) {
            logger.error("===获取jedis实例失败 ==> {}===", e.getMessage());
            throw new MyException(ResponseCodeEnum.REDIS_JEDIS_POOL_ERROR);
        }
    }

    /**
     * 释放jedis资源
     *
     * @param jedis
     */
    public void closeJedis(final Jedis jedis) {
        if (jedis != null && jedis.isConnected()) {
            jedis.close();
        }
    }

    /**
     * Tips: 判断key是否存在
     *
     * @param key 需要判断的key
     * @return 若存在返回true，否则返回false，若未能获取连接池中的jedis实例，则返回false
     * @author lavender
     */
    public Boolean exists(String key) {
        key = cachePrefix + key;
        Jedis jedis = getJedis();
        boolean flag = false;
        if (jedis != null) {
            flag = jedis.exists(key);
            logger.info("判断redis中是否存在key[{}]结果为：{}", key, flag);
            closeJedis(jedis);
        }
        return flag;
    }

    /**
     * 延长key的时间
     *
     * @param key
     * @param second
     * @author zhaop
     * @date 2018年7月31日上午11:52:51
     */
    public void expire(String key, Integer second) {
        key = cachePrefix + key;
        Jedis jedis = getJedis();
        if (jedis != null) {
            if (second != null) {
                jedis.expire(key, second);
                logger.info("向redis中expire，key[{}],,second[{}]", key, second);
            }
            closeJedis(jedis);
        }
    }

    /**
     * hset
     *
     * @param key
     * @param field
     * @return
     * @author zhaopeng
     * @date 2019年2月21日
     */
    public Long hset(String key, String field, String value) {
        key = cachePrefix + key;
        Jedis jedis = getJedis();
        if (jedis != null) {
            Long res = jedis.hset(key, field, value);
            logger.info("从redis中hset key[{}]field[{}]的值[{}]]", key, field, value);
            closeJedis(jedis);
            return res;
        }
        return null;
    }

    /**
     * Tips: 向redis中设置key
     *
     * @param key 键
     * @param value 值
     * @param second 有效期秒 若不设置时效，传入null即可
     * @author lavender
     */
    public String set(String key, String value, Integer second) {
        key = cachePrefix + key;
        Jedis jedis = getJedis();
        String res = null;
        if (jedis != null) {
            res = jedis.set(key, value);
            if (second != null) {
                jedis.expire(key, second);
            }
            logger.info("向redis中设置key[{}],value[{}],second[{}]", key, value, second);
            closeJedis(jedis);
        }
        return res;
    }

    /**
     * 带锁性质的set
     *
     * @param key
     * @param value
     * @param nxxx
     * @param expx
     * @param time
     * @return
     */
    public String set(String key, String value, String nxxx, String expx, long time) {
        key = cachePrefix + key;
        Jedis jedis = getJedis();
        String res = null;
        if (jedis != null) {
            res = jedis.set(key, value, nxxx, expx, time);
            logger.info("向redis中设置key[{}],value[{}],nxxx[{}],expx[{}],time[{}]", key, value, nxxx, expx,time);
            closeJedis(jedis);
        }
        return res;
    }

    /**
     * hget
     *
     * @param key
     * @param field
     * @return
     * @author zhaop
     * @date 2018年9月26日下午3:05:04
     */
    public String hget(String key, String field) {
        key = cachePrefix + key;
        Jedis jedis = getJedis();
        if (jedis != null) {
            String string = jedis.hget(key, field);
            logger.info("从redis中获取key[{}]field[{}]的值[{}]]", key, field, string);
            closeJedis(jedis);
            return string;
        }
        return null;
    }

    /**
     * Tips: 从redis中获取key对应的值
     *
     * @param key 键
     * @return key对应的值，若jedis实例获取失败，则返回null
     * @author lavender
     */
    public String get(String key) {
        key = cachePrefix + key;
        Jedis jedis = getJedis();
        if (jedis != null) {
            String string = jedis.get(key);
            logger.info("从redis中获取key[{}]的值[{}]]", key, string);
            closeJedis(jedis);
            return string;
        }
        return null;
    }

    /**
     * Tips: 从redis中删除key
     *
     * @param key 需要删除的key
     * @author lavender
     */
    public void del(String key) {
        key = cachePrefix + key;
        Jedis jedis = getJedis();
        if (jedis != null) {
            jedis.del(key);
            logger.info("从redis中移除key[{}]", key);
            closeJedis(jedis);
        }
    }
}
