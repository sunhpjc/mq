package com.sunhp.activiti.receive;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author sunhp
 * @Description
 * @Date 2020/4/16 13:44
 **/
@Component
@RabbitListener(queues = "directQueue")
public class DirectReceiver {
    @RabbitHandler
    public void process(Map receiveMessage){

        System.out.println("=============收取到的消息："+receiveMessage.toString()+"===================");
    }
}
