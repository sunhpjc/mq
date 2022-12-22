package com.sunhp.utils.data;

import lombok.Data;

/**
 * @author sunhp
 * @Description
 * @Date 2022/12/22 10:57
 **/
@Data
public class SendSms {
    /**
     * 批次号
     */
    private String batchNo;
    /**
     * 应用编号
     */
    private String appCode;
    /**
     * 模板编号
     */
    private String templateId;
    /**
     * 短信内容
     */
    private String smsContent;
    /**
     * 短信通道
     */
    private String smsMark;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 更新人
     */
    private String updateUser;
    /**
     * 每批次数量
     */
    private Integer total;
}
