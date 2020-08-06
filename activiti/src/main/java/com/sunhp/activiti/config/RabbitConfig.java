package com.sunhp.activiti.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author sunhp
 * @Description RabbitMQ配置类
 * @Date 2020/4/16 10:35
 **/
@Configuration
public class RabbitConfig {
    private static final Logger logger = LoggerFactory.getLogger(RabbitConfig.class);

    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.port}")
    private int port;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;

    //队列 起名：directQueue,使用直连型路由===>注意Queue不要导成java.util下的了，要是rabbit.core的
    @Bean
    public Queue directQueue(){
        return new Queue("directQueue",true);//true 是否持久
    }
    //direct交换机 起名directExchange
    @Bean
    DirectExchange directExchange(){
        return new DirectExchange("directExchange");
    }
    //绑定，将队列和交换机绑定，并设置用于匹配的路由键，directRouting
    @Bean
    Binding directBinding(){
        return BindingBuilder.bind(directQueue()).to(directExchange()).with("directRouting");
    }
}
