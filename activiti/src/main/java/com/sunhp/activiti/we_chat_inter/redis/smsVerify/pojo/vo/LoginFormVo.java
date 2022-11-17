package com.sunhp.activiti.we_chat_inter.redis.smsVerify.pojo.vo;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sunhp
 * @Description 登录请求体
 * @Date 2022/11/11 16:15
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginFormVo {
    @NotEmpty(message = "电话号码不能为空")
    private String phone;
    private String verifyCode;
    private String password;
    @NotEmpty(message = "登录类型不能为空")
    private String loginType;
}
