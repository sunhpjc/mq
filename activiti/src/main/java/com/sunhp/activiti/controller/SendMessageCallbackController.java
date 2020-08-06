package com.sunhp.activiti.controller;

import com.sunhp.activiti.receive_producter.DirectRoutingProducter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sunhp
 * @Description 回调测试
 * @Date 2020/4/29 11:13
 **/
@RestController
@RequestMapping(value = "rabbit_call")
public class SendMessageCallbackController {
    @Resource
    private DirectRoutingProducter producter;

    @GetMapping(value = "sendMessageCallback")
    /*
     * 直连型交换机
     * */
    public String sendMessage(String id,String message){
        Map<String,String> map = new HashMap<>();
        map.put("id",id);
        map.put("message",message);
        //将消息携带绑定键值：directRouting发送到交换机directExchange
        producter.send_callback(map);
        return "ok";
    }
}
