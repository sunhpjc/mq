package com.sunhp.rocketmq.service.impl;

import com.sunhp.rocketmq.entity.Sms;
import com.sunhp.rocketmq.service.SmsService;
import org.apache.ibatis.cursor.Cursor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author sunhp
 * @Description
 * @Date 2021/1/12 14:53
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class SmsServiceImplTest {

    @Resource
    private SmsService smsService;

    @Test
    public void smsCursor() {
        smsService.smsCursor(10);
    }
}