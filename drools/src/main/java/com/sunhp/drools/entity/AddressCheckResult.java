package com.sunhp.drools.entity;

import lombok.Data;

/**
 * @author sunhp
 * @Description
 * @Date 2021/1/20 16:16
 **/
@Data
public class AddressCheckResult {
    //true通过校验，false 未通过
    private boolean postCodeResult = false;
}
