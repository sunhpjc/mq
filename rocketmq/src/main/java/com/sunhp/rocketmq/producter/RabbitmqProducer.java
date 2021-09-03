package com.sunhp.rocketmq.producter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author sunhp
 * @Description
 * @Date 2021/8/30 10:46
 **/
@RestController
@RequestMapping(value = "/rabbit")
public class RabbitmqProducer implements RabbitTemplate.ConfirmCallback {
    private static final Logger logger = LoggerFactory.getLogger(RabbitmqProducer.class);

/*    @Resource
    private RabbitTemplate rabbitTemplate;*/
    @Autowired
    private BeanFactory beanFactory;

    @GetMapping(value = "/sendMessage")
    public Boolean sendMessage(){
        boolean flag = false;
        Map<String, String> map = new HashMap<>();
        map.put("1", "消息1");
        map.put("2","消息2");
        map.put("3", "消息3");

        RabbitTemplate rabbitTemplate = beanFactory.getBean(RabbitTemplate.class);
        rabbitTemplate.setConfirmCallback(this::confirm);

        String reqNo = UUID.randomUUID().toString();
        CorrelationData correlationData = new CorrelationData(reqNo);
        try {
            rabbitTemplate.convertAndSend("smsDirectExchange","smsDirectRouting", map, correlationData);
            flag = true;
        } catch (AmqpException e) {
            logger.info("发送消息异常,消息编号:{},异常:{}" ,reqNo ,e);
        }
        return flag;
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String s) {
        if(ack){
            logger.info("生产者消息发送成功,消息编号:{}", correlationData.getId());
        }else {
            logger.info("生产者消息发送失败,消息编号:{}", correlationData.getId());
        }
    }
}
