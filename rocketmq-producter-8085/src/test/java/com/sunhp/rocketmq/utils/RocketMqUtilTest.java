package com.sunhp.rocketmq.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @author sunhp
 * @Description
 * @Date 2021/1/13 14:57
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class RocketMqUtilTest {
    @Resource(name = "rocketMqUtil")
    private RocketMqUtil rocketMqUtil;

    @Test
    public void syncProducer() {
        String test = "test";
        try {
//            rocketMqUtil.SyncProducer(test,"testTag");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void consumer() {
        String topic = "smsTopic";
        String[] strings = {"tagSms"};

        try {
            rocketMqUtil.consumer(topic, strings);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}