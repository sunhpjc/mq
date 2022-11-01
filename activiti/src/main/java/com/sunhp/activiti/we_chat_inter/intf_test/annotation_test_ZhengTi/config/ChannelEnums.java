package com.sunhp.activiti.we_chat_inter.intf_test.annotation_test_ZhengTi.config;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author sunhp
 * @Description
 * @Date 2022/11/1 11:46
 **/
@Getter
@AllArgsConstructor
public enum ChannelEnums {
    CHANNEL_ONE("CHANNEL_ONE", "渠道一"),
    CHANNEL_TWO("CHANNEL_TWO", "渠道二"),
    CHANNEL_THREE("CHANNEL_THREE", "渠道三"),
    ;

    private String code;
    private String desc;

    public static ChannelEnums find(String code) {
        return Arrays.stream(ChannelEnums.values())
            .filter(input -> input.getCode()
                .equals(code))
            .findFirst()
            .orElse(null);
    }
}