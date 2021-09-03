package com.sunhp.rocketmq.comsumer;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author sunhp
 * @Description
 * @Date 2021/8/30 11:11
 **/
@Component
@RabbitListener(queues = "smsDirectQueue")
public class RabbitmqConsumer {
    private static final Logger logger = LoggerFactory.getLogger(RabbitmqConsumer.class);
    int count = 0;

    @RabbitHandler
    public void getMessage(Map<String,String> map, Channel channel, Message message){
        int times = count++;
        boolean flag = false;
        logger.info("消费次数：第{}次", count);
        try {
            int i = 9/0;
            flag = true;
        } catch (Exception e) {
            logger.info("消费者消费异常：{}",e);
            throw e;
        }
    }
}
