package com.sunhp.activiti.we_chat_inter.annotations_interception;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * @author sunhp
 * @Description 注解拦截controller
 * @Date 2022/10/14 9:37
 **/
@RequestMapping(value = "/AnnotationsInterception")
@RestController
@Slf4j
public class AnnotationsInterceptionController {

    @GetMapping("test1")
    @OnlyOutAccess
    public void test1(){
        log.info("test1");
    }

    @GetMapping("test2")
    public void test2(){
        log.info("test2");
    }
}
