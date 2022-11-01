package com.sunhp.activiti.we_chat_inter.intf_test.annotation_test_ZhengTi.config;

import java.util.Collection;
import java.util.HashMap;

import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.sunhp.activiti.exception.MyException;
import com.sunhp.activiti.we_chat_inter.intf_test.annotation_test_ZhengTi.service.SmsService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author sunhp
 * @Description 初始化实现类
 * @Date 2022/10/26 15:11
 **/
@Component
@Slf4j
public class StrategyFactory implements CommandLineRunner, ApplicationContextAware {
    private volatile ApplicationContext applicationContext;
    private static HashMap<ChannelEnums, SmsService> channelMap;

    @Override
    public void run(String... args) {
        initChannelMap();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 初始化map
     */
    private void initChannelMap(){
        log.info("====>初始化渠道实现开始<====");
        Collection<SmsService> smsServices = this.applicationContext.getBeansOfType(SmsService.class)
            .values();
        channelMap = new HashMap<>(smsServices.size());
        smsServices.forEach(smsService -> {
            Class<? extends SmsService> smsServiceClass = smsService.getClass();
            ChannelTypeAnnotation annotation = smsServiceClass.getAnnotation(ChannelTypeAnnotation.class);
            if(null != annotation){
                channelMap.put(annotation.channelType(), smsService);
            }
        });
        channelMap.forEach((k, v) -> {
            log.info("渠道key:{} 渠道value:{}", k, v);
        });
        log.info("====>初始化渠道实现结束<====");
    }

    /**
     * 获取渠道信息
     * @param channelEnum
     * @return
     */
    public static SmsService getSmsServiceByChannelType(ChannelEnums channelEnum){
        SmsService smsService = channelMap.get(channelEnum);
        if(null == smsService){
            throw new MyException("不支持的渠道类型");
        }
        return smsService;
    }
}
