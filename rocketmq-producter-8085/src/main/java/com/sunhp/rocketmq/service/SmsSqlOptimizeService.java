package com.sunhp.rocketmq.service;

import com.sunhp.rocketmq.vo.response.ResultVO;

/**
 * @author sunhp
 * @Description
 * @Date 2021/4/19 15:36
 **/
public interface SmsSqlOptimizeService {
    /**
     * 测试多线程插入数据
     * @return
     */
    ResultVO buildSmsTest(int times, String appCode, String smsMark);

    /**
     * 插入数据
     * @param times
     * @param appCode
     * @return
     */
    ResultVO buildSms(int times, String appCode, String smsMark);

}
