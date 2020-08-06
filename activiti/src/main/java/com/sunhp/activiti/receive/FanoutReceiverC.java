package com.sunhp.activiti.receive;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author sunhp
 * @Description
 * @Date 2020/4/16 15:42
 **/
@Component
@RabbitListener(queues = "fanout.C")
public class FanoutReceiverC {
    @RabbitHandler
    public void process(Map testMessage){
        System.out.println("FanoutReceiverC消费消息"+testMessage.toString());
    }
}
