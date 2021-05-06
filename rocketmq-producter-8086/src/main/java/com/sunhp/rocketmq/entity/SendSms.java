package com.sunhp.rocketmq.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 发送短信表(SendSms)实体类
 *
 * @author sunhp
 * @since 2021-04-19 15:45:24
 */
public class SendSms implements Serializable {
    private static final long serialVersionUID = 989720662596877985L;
    /**
    * 短信序列号
    */
    private Object id;
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


    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTargetPhone() {
        return targetPhone;
    }

    public void setTargetPhone(String targetPhone) {
        this.targetPhone = targetPhone;
    }

    public Integer getSmsStatus() {
        return smsStatus;
    }

    public void setSmsStatus(Integer smsStatus) {
        this.smsStatus = smsStatus;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    public Integer getArriveStatus() {
        return arriveStatus;
    }

    public void setArriveStatus(Integer arriveStatus) {
        this.arriveStatus = arriveStatus;
    }

    public String getSmsMark() {
        return smsMark;
    }

    public void setSmsMark(String smsMark) {
        this.smsMark = smsMark;
    }

    public Integer getChannelMark() {
        return channelMark;
    }

    public void setChannelMark(Integer channelMark) {
        this.channelMark = channelMark;
    }

    public Integer getAgainMark() {
        return againMark;
    }

    public void setAgainMark(Integer againMark) {
        this.againMark = againMark;
    }

    public Integer getAgainCount() {
        return againCount;
    }

    public void setAgainCount(Integer againCount) {
        this.againCount = againCount;
    }

    public Integer getBackStatus() {
        return backStatus;
    }

    public void setBackStatus(Integer backStatus) {
        this.backStatus = backStatus;
    }

    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

}