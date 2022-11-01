package com.sunhp.activiti.we_chat_inter.intf_test;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * @author sunhp
 * @Description
 * @Date 2022/10/24 15:48
 **/
@RestController
@RequestMapping("smsTest")
@Slf4j
public class SmsTestController {
    private final Map<String, SmsService> smsServiceMap;

    public SmsTestController(Map<String, SmsService> smsServiceMap) {this.smsServiceMap = smsServiceMap;}

    @GetMapping("testOne")
    public void testOne() {
        SmsService smsService = smsServiceMap.get("smsChannelOne");
        sendSms(smsService);
    }

    private void sendSms(SmsService smsService) {
        for (int i = 0; i < 3; i++) {
            try {
                switch (i) {
                    case 0:
                        smsService.sendSmsBatch();
                        break;
                    case 1:
                        smsService.sendSmsSingle();
                        break;
                    case 2:
                        smsService.smsBackStatus();
                        break;
                }
            } catch (Exception e) {
                // log.info("服务调用失败：{}", e);
                continue;
            }
        }
    }

    @GetMapping("testTwo")
    public void testTwo() {
        SmsService smsService = smsServiceMap.get("smsChannelTwo");
        sendSms(smsService);
    }

    @GetMapping("testThree")
    public void testThree() {
        SmsService smsService = smsServiceMap.get("smsChannelThree");
        sendSms(smsService);
    }
}
