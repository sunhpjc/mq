package com.sunhp.activiti.we_chat_inter.intf_test.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author sunhp
 * @Description 渠道一
 * 支持单条和回执
 * @Date 2022/10/24 15:41
 **/
@Service("smsChannelOne")
@Slf4j
public class SmsChannelOne extends SmsAbstractService{
    @Override
    public Boolean sendSmsSingle() {
        log.info("渠道一单条发送");
        return true;
    }

    @Override
    public Map<Integer, String> smsBackStatus() {
        log.info("渠道一回执");
        return null;
    }
}
