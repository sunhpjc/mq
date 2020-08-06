package com.sunhp.rocketmq.producter;

import com.sunhp.rocketmq.entity.User;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sunhp
 * @Description 生产者
 * @Date 2020/4/30 10:50
 **/
@RestController
@RequestMapping("rocketmq")
public class Producter1 {
    @Resource
    private RocketMQTemplate rocketMQTemplate;
    
    @RequestMapping(value = "sendMessage",method = RequestMethod.GET)
    public String sendMessage(/*String name, String phone*/){
       /* String[] resultList = new String[10];
        for (int i = 0; i < 10; i++) {
            SendResult sendResult = rocketMQTemplate.syncSend("topicTest","hello");
            resultList[i] = sendResult.toString();
        }*/
       //rocketMQTemplate.syncSend("topicTest","hello world");
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 500000; i++) {
            User user = new User();
            user.setUsername(String.format("测试%s",i));
            user.setPhone(String.format("电话%s",i));
            userList.add(user);
        }
        for (User user :
                userList) {
            rocketMQTemplate.syncSendOrderly("orderTopic2",user,"order2");
        }
       return "success";
    }
}
