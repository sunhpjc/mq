package com.sunhp.rocketmq.config;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author sunhp
 * @Description
 * @Date 2021/1/8 14:03
 **/
@Configuration
@EnableAutoConfiguration
@Data
public class RedisConfig {
    private static Logger logger = LoggerFactory.getLogger(RedisConfig.class);
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.database}")
    private int database;

    @Value("${spring.redis.max-active}")
    private int maxActive;
    @Value("${spring.redis.max-idle}")
    private int maxIdle;
    @Value("${spring.redis.min-idle}")
    private int minIdle;
    @Value("${spring.redis.max-wait}")
    private int maxWait;

    @Bean
    public JedisPoolConfig getRedisConfig(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setMaxTotal(maxActive);
        return config;
    }

    @Bean
    public JedisPool getJedisPool(){
        JedisPoolConfig config = getRedisConfig();
        logger.info("==========config{}", JSONObject.toJSONString(config));
        JedisPool pool = null;
        if(StringUtils.isNotBlank(password)){
            pool = new JedisPool(config,host,port,timeout,password,database);
        }else{
            pool = new JedisPool(config,host,port,timeout);
        }
        logger.info("init JredisPool ...");
        return pool;
    }
}
