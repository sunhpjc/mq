package com.sunhp.rocketmq.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (SmsStatistics)实体类
 *
 * @author sunhp
 * @since 2021-04-20 11:38:48
 */
@Data
public class SmsStatistics implements Serializable {
    private static final long serialVersionUID = 532073845821493805L;
    /**
    * 自增id
    */
    private Long id;
    /**
    * 精确到小时 如 2021-04-20 03:00:00
    */
    private Date timeFlag;
    /**
    * 当前小时发送短信总数
    */
    private Long smsTotal;
    /**
    * 当前小时发送短信成功数
    */
    private Long smsSuccess;
    /**
    * 最后一条短息的id
    */
    private Long previousId;
    /**
    * 0无效，1有效
    */
    private Integer status;
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