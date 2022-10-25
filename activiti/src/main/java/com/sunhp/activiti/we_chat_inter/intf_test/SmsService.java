package com.sunhp.activiti.we_chat_inter.intf_test;

import java.util.List;
import java.util.Map;

/**
 * @author sunhp
 * @Description 短信服务
 * @Date 2022/10/24 13:45
 **/
public interface SmsService {
    /**
     * 短信批量发送
     * @return
     */
    Boolean sendSmsBatch();

    /**
     * 短信单条发送
     * @return
     */
    Boolean sendSmsSingle();

    /**
     * 获取回执状态
     * @return
     */
    Map<Integer,String> smsBackStatus();
}
