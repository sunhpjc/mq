package com.sunhp.activiti.we_chat_inter.guava.enums;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author sunhp
 * @Description
 * @Date 2022/10/21 9:33
 **/
@Getter
@AllArgsConstructor
public enum CacheTypeEnums {
    USER_CACHE("userCache","用户缓存"),
    USER_LIST_CACHE("userListCache","用户列表缓存"),
    ;

    private String code;
    private String desc;

    public static CacheTypeEnums find(String code) {
        return Arrays.stream(CacheTypeEnums.values())
            .filter(input -> input.getCode()
                .equals(code))
            .findFirst()
            .orElse(null);
    }
}
