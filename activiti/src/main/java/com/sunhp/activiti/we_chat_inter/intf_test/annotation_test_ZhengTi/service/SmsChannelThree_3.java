package com.sunhp.activiti.we_chat_inter.intf_test.annotation_test_ZhengTi.service;

import org.springframework.stereotype.Component;

import com.sunhp.activiti.we_chat_inter.intf_test.annotation_test_ZhengTi.config.ChannelEnums;
import com.sunhp.activiti.we_chat_inter.intf_test.annotation_test_ZhengTi.config.ChannelTypeAnnotation;

import lombok.extern.slf4j.Slf4j;

/**
 * @author sunhp
 * @Description 渠道三
 * 支持单条和批量
 * @Date 2022/10/24 15:44
 **/
@Component
@Slf4j
@ChannelTypeAnnotation(channelType = ChannelEnums.CHANNEL_THREE)
public class SmsChannelThree_3 extends SmsAbstractService {
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
