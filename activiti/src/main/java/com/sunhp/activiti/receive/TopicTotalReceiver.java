package com.sunhp.activiti.receive;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author sunhp
 * @Description 主题交换机队列二
 * @Date 2020/4/16 14:38
 **/
@Component
@RabbitListener(queues = "topic.woman")
public class TopicTotalReceiver {
    @RabbitHandler
    public void process(Map testMessage){
        System.out.println("TopicTotalReceiver接收的消息，第二个队列："+testMessage.toString());
    }
}
