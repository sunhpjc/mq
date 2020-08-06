package com.sunhp.activiti.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author sunhp
 * @Description 扇形交换机配置
 * @Date 2020/4/16 15:31
 **/
@Configuration
public class RabbitFanoutConfig {
    /*
    * 创建三个队列：fanout.A fanout.B fanout.C
    * 将三个队列都绑定在交换机 fanoutExchange上
    * 扇形交换机，路由键不需要做配置，配置也没有用
    * */
    @Bean
    public Queue fanoutQueueA(){
        return new Queue("fanout.A");
    }
    @Bean
    public Queue fanoutQueueB(){
        return new Queue("fanout.B");
    }
    @Bean
    public Queue fanoutQueueC(){
        return new Queue("fanout.C");
    }

    //定义交换机
    @Bean
    FanoutExchange fanoutExchange(){
        return new FanoutExchange("fanoutExchange");
    }
    //绑定交换机
    @Bean
    Binding bindingExchangeA(){
        return BindingBuilder.bind(fanoutQueueA()).to(fanoutExchange());
    }
    @Bean
    Binding bindingExchangeB(){
        return BindingBuilder.bind(fanoutQueueB()).to(fanoutExchange());
    }
    @Bean
    Binding bindingExchangeC(){
        return BindingBuilder.bind(fanoutQueueC()).to(fanoutExchange());
    }
}
