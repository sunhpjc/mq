package com.sunhp.rocketmq.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 发送短信表(SendSms)实体类
 *
 * @author sunhp
 * @since 2021-04-19 15:46:12
 */
@Data
public class SendSms implements Serializable {
    private static final long serialVersionUID = -26733892419441295L;
    /**
    * 短信序列号
    */
    private Long id;
    /**
    * 批次号
    */
    private String batchNo;
    /**
    * 应用id
    */
    private String appCode;
    /**
    * 模板id
    */
    private String templateId;
    /**
    * 目标电话
    */
    private String targetPhone;
    /**
    * 短信状态 0表示未发送，1表示已发送，2表示发送失败
    */
    private Integer smsStatus;
    /**
    * 短信内容
    */
    private String smsContent;
    /**
    * 短信送达状态 0：失败，1：成功
    */
    private Integer arriveStatus;
    /**
    * 短信标识位
    */
    private String smsMark;
    /**
    * 通道限定位 1：限定 0：不限定
    */
    private Integer channelMark;
    /**
    * 重发标志位 1：重发 0：不重发
    */
    private Integer againMark;
    /**
    * 重发次数 指定次数，不包含第一次
    */
    private Integer againCount;
    /**
    * 0：未拉取回执 1：已拉取回执
    */
    private Integer backStatus;
    /**
    * 用户自定义id
    */
    private String customId;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 创建人
    */
    private String createUser;
    /**
    * 更新时间
    */
    private Date updateTime;
    /**
    * 更新人
    */
    private String updateUser;
}