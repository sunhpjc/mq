package com.sunhp.rocketmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
//@NacosPropertySource(dataId = "sms_nacos", autoRefreshed = true, groupId = "SMS_GROUP")
public class RocketmqApplication8086 {

    public static void main(String[] args) {
        SpringApplication.run(RocketmqApplication8086.class, args);
    }
}
