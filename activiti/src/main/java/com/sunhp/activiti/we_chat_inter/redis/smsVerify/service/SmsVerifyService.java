package com.sunhp.activiti.we_chat_inter.redis.smsVerify.service;

import com.sunhp.activiti.vo.ResultVO;
import com.sunhp.activiti.we_chat_inter.redis.smsVerify.pojo.dto.LoginFormDto;

/**
 * @author sunhp
 * @Description 短信登录服务
 * @Date 2022/11/11 11:29
 **/
public interface SmsVerifyService {
    /**
     * 发送验证码
     * @param phone
     * @return
     */
    ResultVO sendCode(String phone);

    /**
     * 登录请求
     * @param loginFormDto
     * @return
     */
    ResultVO login(LoginFormDto loginFormDto);
}
