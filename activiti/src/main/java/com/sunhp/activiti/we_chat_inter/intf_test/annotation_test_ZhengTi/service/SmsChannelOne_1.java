package com.sunhp.activiti.we_chat_inter.intf_test.annotation_test_ZhengTi.service;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.sunhp.activiti.we_chat_inter.intf_test.annotation_test_ZhengTi.config.ChannelEnums;
import com.sunhp.activiti.we_chat_inter.intf_test.annotation_test_ZhengTi.config.ChannelTypeAnnotation;

import lombok.extern.slf4j.Slf4j;

/**
 * @author sunhp
 * @Description 渠道一
 * 支持单条和回执
 * @Date 2022/10/24 15:41
 **/
@Component
@Slf4j
@ChannelTypeAnnotation(channelType = ChannelEnums.CHANNEL_ONE)
public class SmsChannelOne_1 extends SmsAbstractService {
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
