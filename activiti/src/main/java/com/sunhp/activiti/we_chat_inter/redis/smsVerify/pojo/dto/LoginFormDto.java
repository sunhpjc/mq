package com.sunhp.activiti.we_chat_inter.redis.smsVerify.pojo.dto;

import com.sunhp.activiti.we_chat_inter.redis.smsVerify.enums.LoginTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sunhp
 * @Description 登录数据传输
 * @Date 2022/11/11 14:08
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginFormDto {
    private String phone;
    private String verifyCode;
    private String password;
    private LoginTypeEnum loginTypeEnum;
}
