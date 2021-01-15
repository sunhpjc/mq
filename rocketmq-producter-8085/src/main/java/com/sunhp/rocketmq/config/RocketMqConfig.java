package com.sunhp.rocketmq.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author sunhp
 * @Description
 * @Date 2021/1/12 17:27
 **/
@Configuration
@Data
public class RocketMqConfig {

//    @Value("${rocket.group-name}")
    private String groupName;
//    @Value("${rocket.name-server}")
    private String nameServer;
//    @Value("${rocket.sms-topic}")
    private String topic;
//    @Value("${rocket.sync-retry-times}")
    private int syncRetryTimes;
//    @Value("${rocket.async-retry-times}")
    private int asyncRetryTimes;
}
