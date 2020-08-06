package com.sunhp.activiti.receive;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author sunhp
 * @Description 主题交换机队列一
 * @Date 2020/4/16 14:28
 **/
@Component
@RabbitListener(queues = "topic.man")
public class TopicReceiver {
    @RabbitHandler
    public void process(Map testMessage1){
        System.out.println("topicMan接收到的消息，第一个队列："+testMessage1.toString());
    }
}
