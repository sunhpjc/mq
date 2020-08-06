package com.sunhp.activiti.controller;

import com.sunhp.activiti.receive_producter.DirectRoutingProducter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sunhp
 * @Description 用于测试rabbit消息推送
 * @Date 2020/4/16 11:06
 **/
@RestController
@RequestMapping(value = "rabbitmq")
public class SendMessageController {
    @Resource
    private RabbitTemplate rabbitTemplate;//使用RabbitTemplate,这里提供了接收/发送等方法


    @GetMapping(value = "sendMessage")
    /*
    * 直连型交换机
    * */
    public String sendMessage(String id,String message){
        Map<String,String> map = new HashMap<>();
        map.put("id",id);
        map.put("message",message);
        //将消息携带绑定键值：directRouting发送到交换机directExchange
       /* DirectRoutingProducter producter = new DirectRoutingProducter();
        producter.send_callback(map);*/
        rabbitTemplate.convertAndSend("directExchange","directRouting",map);//使用确认回调之后这个不再使用
        return "ok";
    }

    @GetMapping(value = "sendMessageTopic1")
    /*
    * topic交换机
    * */
    public String sendMessageTopic1(String id,String message){
        Map<String,String> map = new HashMap<>();
        map.put("id",id);
        map.put("message",message);
        //将消息携带绑定键值：topic.man发送到交换机topicExchange
        rabbitTemplate.convertAndSend("topicExchange","topic.man",map);
        return "ok";
    }
    @GetMapping(value = "sendMessageTopic2")
    /*
     * topic交换机
     * */
    public String sendMessageTopic2(String id,String message){
        Map<String,String> map = new HashMap<>();
        map.put("id",id);
        map.put("message",message);
        //将消息携带绑定键值：topic.woman发送到交换机topicExchange
        rabbitTemplate.convertAndSend("topicExchange","topic.woman",map);
        return "ok";
    }
    /*
    * 扇形交换机推送消息
    * */
    @GetMapping(value = "sendMessageFanout")
    public String sendMessageFanout(String id,String message){
        Map<String,String> map = new HashMap<>();
        map.put("id",id);
        map.put("message",message);
        //发送到交换机fanoutExchange
        rabbitTemplate.convertAndSend("fanoutExchange",null,map);
        return "ok";
    }
}
