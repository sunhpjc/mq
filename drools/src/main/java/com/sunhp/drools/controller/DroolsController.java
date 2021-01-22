package com.sunhp.drools.controller;

import com.sunhp.drools.entity.Address;
import com.sunhp.drools.entity.AddressCheckResult;
import com.sunhp.drools.servic.Routers;
import org.kie.api.runtime.KieSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/drools")
public class DroolsController {

    @Resource
    KieSession kieSession;
    @Resource
    Routers routers;

    @GetMapping(value = "/address")
    public void test1(){
        Address address = new Address();
        address.setPostCode("99425");
        address.setStreet("test");

        AddressCheckResult result = new AddressCheckResult();
        kieSession.insert(address);
        kieSession.insert(result);
        int ruleFiredCount = kieSession.fireAllRules();
        System.out.println("触发了"+ruleFiredCount+"条规则");

        if (result.isPostCodeResult()){
            System.out.println("规则校验通过");
        }
    }

    @GetMapping(value = "/sms")
    public void test2(){
        String appCode = "test";
        int type = 1;

        boolean appCodeStatus = routers.appCodeStatus(appCode);
        boolean routerStatus = routers.routerStatus(appCode, type);


    }
}