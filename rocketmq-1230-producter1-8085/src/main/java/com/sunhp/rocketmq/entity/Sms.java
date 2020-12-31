package com.sunhp.rocketmq.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (Sms)实体类
 *
 * @author makejava
 * @since 2020-12-31 11:27:55
 */
@Data
public class Sms implements Serializable {
    private static final long serialVersionUID = -88484694573133316L;
    /**
    * 自增id
    */
    private Long id;
    /**
    * 电话号码
    */
    private String phone;
    /**
    * 内容
    */
    private String content;
    /**
    * 0：待发送，1：发送成功，2：发送中，3：发送失败
    */
    private Integer status;
}