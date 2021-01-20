//package com.sunhp.rocketmq.controller;
//
//import com.alibaba.nacos.api.config.annotation.NacosValue;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import static org.springframework.web.bind.annotation.RequestMethod.GET;
//
///**
// * @author sunhp
// * @Description
// * @Date 2021/1/19 15:47
// **/
//@RestController("/nacos")
//public class NacosController {
//    @Value("${useLocalCache}")
//    private boolean useLocalCache;
//
////    @NacosValue(value = "${xxl.job.executor.port}", autoRefreshed = true)
//    private int port;
//
//    public void setUseLocalCache(boolean useLocalCache) {
//        this.useLocalCache = useLocalCache;
//    }
//
//    public void setPort(int port) {
//        this.port = port;
//    }
//
//    @RequestMapping(value = "/get", method = GET)
//    @ResponseBody
//    public String get() {
//        return useLocalCache+"端口："+port;
//    }
//}
