package com.sunhp.nacos.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author sunhp
 * @Description
 * @Date 2021/1/19 15:47
 **/
@RestController
@RequestMapping("nacos")
public class NacosController {
    @NacosValue(value = "${useLocalCache}", autoRefreshed = true)
    private boolean useLocalCache;

//    @NacosValue(value = "${xxl.job.test}", autoRefreshed = true)
//    private String test;

//    @NacosValue(value = "${xxl.second:}", autoRefreshed = true)
//    private String second;

    public void setUseLocalCache(boolean useLocalCache) {
        this.useLocalCache = useLocalCache;
    }

//    public void setTest(String test) {
//        this.test = test;
//    }

//    public void setSecond(String second) {
//        this.second = second;
//    }

    @RequestMapping(value = "/getTest", method = GET)
    @ResponseBody
    public Boolean get() {
        return useLocalCache;
    }

//    @RequestMapping(value = "/getTest1", method = GET)
//    @ResponseBody
//    public String get1() {
//        return test;
//    }

//    @RequestMapping(value = "/getTest2", method = GET)
//    @ResponseBody
//    public String get2() {
//        return second;
//    }
}
