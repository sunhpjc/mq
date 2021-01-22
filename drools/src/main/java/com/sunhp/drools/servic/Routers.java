package com.sunhp.drools.servic;

import org.springframework.stereotype.Service;

/**
 * @author sunhp
 * @Description
 * @Date 2021/1/20 16:38
 **/
@Service
public class Routers {
    /**
     * 查询应用状态
     * @param appCode
     * @return
     */
    public boolean appCodeStatus(String appCode){
        //查询appCode状态
        boolean flag = false;
        if("test".equals(appCode)){
            flag = true;
        }
        return flag;
    }

    /**
     * 查询路由状态
     * @param appCode
     * @param type
     * @return
     */
    public boolean routerStatus(String appCode, int type){
        //查询路由状态
        boolean flag = false;
        if("test".equals(appCode) && 1 == type){
            flag = true;
        }
        return flag;
    }
}
