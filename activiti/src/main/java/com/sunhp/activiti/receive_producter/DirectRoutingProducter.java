package com.sunhp.activiti.receive_producter;

import com.sunhp.activiti.service.impl.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.UUID;

/**
 * @author sunhp
 * @Description 直连路由
 * @Date 2020/4/29 9:42
 **/
@Service
@Component
public class DirectRoutingProducter implements RabbitTemplate.ConfirmCallback {
    private static final Logger logger = LoggerFactory.getLogger(DirectRoutingProducter.class);

    @Resource
    private RedisService redisService;
    @Resource
    private RabbitTemplate rabbitTemplate;//使用RabbitTemplate,这里提供了接收/发送等方法

    public void send_callback(Map map){
        logger.info("开始生产信息，{}",map);
        //注意：这里确认还有问题
        rabbitTemplate.setConfirmCallback(this::confirm);
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("directExchange","directRouting",map,correlationData);
    }
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        System.out.println("CallBackConfirm UUID："+correlationData.getId());
        if(b){
            System.out.println("消息消费成功，计算值为："+count());
        }else {
            System.out.println("消息消费失败");
        }
        if(s!=null){
            System.out.println("CallBackConfirm Cause:"+s);
        }
    }
    private int count(){
        int i=3,j=9;
        return i+j;
    }
}
