package com.sunhp.activiti.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sunhp
 * @Description
 * @Date 2020/4/9 17:26
 **/
//@ComponentScan(basePackages = {"com.sunhp.*"})
@RestController
@RequestMapping(value = "test")
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);
    @RequestMapping(value = "/te")
    public String test(){
        logger.debug("======测试日志=====");
        logger.info("======测试日志=====");
        logger.error("======测试日志=====");
        return "test";
    }
}
