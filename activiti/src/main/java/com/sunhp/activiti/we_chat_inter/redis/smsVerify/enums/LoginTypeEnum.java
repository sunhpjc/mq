package com.sunhp.activiti.we_chat_inter.redis.smsVerify.enums;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author sunhp
 * @Description 登录类型
 * @Date 2022/11/11 14:39
 **/
@Getter
@AllArgsConstructor
public enum LoginTypeEnum {
    PHONE("0", "手机号"),
    USER_NAME("1", "用户名"),
    ;

    private String code;
    private String value;

    public static LoginTypeEnum find(String code) {
        return Arrays.stream(LoginTypeEnum.values())
            .filter(input -> input.getCode()
                .equals(code))
            .findFirst()
            .orElse(null);
    }

}
