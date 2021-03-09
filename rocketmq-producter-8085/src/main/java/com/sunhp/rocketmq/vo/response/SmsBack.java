package com.sunhp.rocketmq.vo.response;

import lombok.Data;

/**
 * @author sunhp
 * @Description 响应回执
 * @Date 2021/2/1 11:38
 **/
@Data
public class SmsBack {
    private String batchNo;
    private String phone;
    private Integer status;

    public SmsBack(String batchNo, String phone, Integer status) {
        this.batchNo = batchNo;
        this.phone = phone;
        this.status = status;
    }
}
