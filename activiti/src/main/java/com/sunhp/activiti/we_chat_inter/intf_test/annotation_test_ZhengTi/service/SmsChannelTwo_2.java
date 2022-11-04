package com.sunhp.activiti.we_chat_inter.intf_test.annotation_test_ZhengTi.service;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.sunhp.activiti.we_chat_inter.intf_test.annotation_test_ZhengTi.config.ChannelEnums;
import com.sunhp.activiti.we_chat_inter.intf_test.annotation_test_ZhengTi.config.ChannelTypeAnnotation;

import lombok.extern.slf4j.Slf4j;

/**
 * @author sunhp
 * @Description 渠道二
 * 支持批量和回执
 * @Date 2022/10/24 15:42
 **/
@Component
@Slf4j
@ChannelTypeAnnotation(channelType = ChannelEnums.CHANNEL_TWO)
public class SmsChannelTwo_2 extends SmsAbstractService {
    @Override
    public Boolean sendSmsBatch() {
        log.info("渠道二批量发送");
        log.debug("渠道二debug");
        return true;
    }

    @Override
    public Map<Integer, String> smsBackStatus() {
        log.info("渠道二回执");
        return null;
    }
}
