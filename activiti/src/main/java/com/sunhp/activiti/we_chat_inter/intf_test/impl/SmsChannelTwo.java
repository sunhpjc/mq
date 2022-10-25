package com.sunhp.activiti.we_chat_inter.intf_test.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author sunhp
 * @Description 渠道二
 * 支持批量和回执
 * @Date 2022/10/24 15:42
 **/
@Service("smsChannelTwo")
@Slf4j
public class SmsChannelTwo extends SmsAbstractService{
    @Override
    public Boolean sendSmsBatch() {
        log.info("渠道二批量发送");
        return true;
    }

    @Override
    public Map<Integer, String> smsBackStatus() {
        log.info("渠道二回执");
        return null;
    }
}
