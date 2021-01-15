package com.sunhp.rocketmq.service.impl;

import com.sunhp.rocketmq.entity.Sms;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author sunhp
 * @Description
 * @Date 2021/1/15 9:29
 **/
//泛型必须和接收的消息类型相同RocketMQListener<T>
@Component  //必须注入spring容器
@RocketMQMessageListener(topic = "topicSms",//topic：和消费者发送的topic相同
        consumerGroup = "smsConsumerGroup", //group：不用和生产者group相同
        selectorExpression = "tagSms")//tag,"*"代表所有
public class RocketmqConsumerTemplate implements RocketMQListener<Sms> {
    private static final Logger logger = LoggerFactory.getLogger(RocketmqConsumerTemplate.class);

    @Override
    public void onMessage(Sms sms) {
        logger.info("消费消息,{}",sms.toString());
    }
}
