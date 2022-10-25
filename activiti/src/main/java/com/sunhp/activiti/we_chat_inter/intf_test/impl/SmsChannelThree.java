package com.sunhp.activiti.we_chat_inter.intf_test.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author sunhp
 * @Description 渠道三
 * 支持单条和批量
 * @Date 2022/10/24 15:44
 **/
@Service("smsChannelThree")
@Slf4j
public class SmsChannelThree extends SmsAbstractService{
    @Override
    public Boolean sendSmsBatch() {
        log.info("渠道三批量发送");
        return null;
    }

    @Override
    public Boolean sendSmsSingle() {
        log.info("渠道三单条发送");
        return null;
    }
}
