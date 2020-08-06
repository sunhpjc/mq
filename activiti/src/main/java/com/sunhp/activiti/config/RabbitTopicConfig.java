package com.sunhp.activiti.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author sunhp
 * @Description 主题交换机测试
 * @Date 2020/4/16 13:58
 **/
@Configuration
public class RabbitTopicConfig {
    //绑定键
    public final static String man = "topic.man";
    public final static String woman = "topic.woman";

    @Bean
    //定义队列
    public Queue firstQueue(){
        return new Queue(RabbitTopicConfig.man);
    }
    @Bean
    public Queue secondQueue(){
        return new Queue(RabbitTopicConfig.woman);
    }

    @Bean
    //定义交换机
    TopicExchange topicExchange(){
        return new TopicExchange("topicExchange");
    }

    //将firstQueue和topicExchange绑定，而且绑定的键值为topic.man
    //这样只要是消息携带的路由键是topic.man才会分发到该队列
    @Bean
    Binding bindingExchange1(){
        return BindingBuilder.bind(firstQueue()).to(topicExchange()).with(man);
    }
    //将secondQueue和topicExchange绑定,而且绑定的键值为用上通配路由键规则topic.#
    //这样只要消息携带的路由键是以topic.开头，都会分发到该队列
    @Bean
    Binding bindingExchange2(){
        return BindingBuilder.bind(secondQueue()).to(topicExchange()).with("topic.#");
    }
}
