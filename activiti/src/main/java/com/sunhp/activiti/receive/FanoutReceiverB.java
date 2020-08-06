package com.sunhp.activiti.receive;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author sunhp
 * @Description
 * @Date 2020/4/16 15:41
 **/
@Component
@RabbitListener(queues = "fanout.B")
public class FanoutReceiverB {
    @RabbitHandler
    public void process(Map testMessage){
        System.out.println("FanoutReceiverB消费消息"+testMessage.toString());
    }
}
