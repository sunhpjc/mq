package com.sunhp.drools.entity;

import lombok.Data;

/**
 * @author sunhp
 * @Description
 * @Date 2021/1/20 16:00
 **/
@Data
public class RoolsEntity {
    private Boolean appCodeStatus;
    private Boolean routerStatus;

    public RoolsEntity(Boolean appCodeStatus, Boolean routerStatus) {
        this.appCodeStatus = appCodeStatus;
        this.routerStatus = routerStatus;
    }
}
