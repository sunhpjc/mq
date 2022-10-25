package com.sunhp.activiti.we_chat_inter.intf_test.impl;

import java.util.Map;

import com.sunhp.activiti.exception.MyException;
import com.sunhp.activiti.we_chat_inter.intf_test.SmsService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author sunhp
 * @Description
 * @Date 2022/10/24 15:32
 **/
@Slf4j
public class SmsAbstractService implements SmsService {
    @Override
    public Boolean sendSmsBatch() {
        log.info("当前渠道不支持批量发送");
        throw new MyException("当前渠道不支持批量发送");
    }

    @Override
    public Boolean sendSmsSingle() {
        log.info("当前渠道不支持单条发送");
        throw new MyException("当前渠道不支持单条发送");
    }

    @Override
    public Map<Integer, String> smsBackStatus() {
        log.info("当前渠道不支持回执获取");
        throw new MyException("当前渠道不支持回执获取");
    }
}
