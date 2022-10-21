package com.sunhp.activiti.we_chat_inter.guava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sunhp.activiti.entity.User;
import com.sunhp.activiti.we_chat_inter.guava.enums.CacheTypeEnums;

import lombok.extern.slf4j.Slf4j;

/**
 * @author sunhp
 * @Description
 * @Date 2022/10/18 11:06
 **/
@RestController
@RequestMapping("guava")
@Slf4j
public class GuavaTestController {
    @Autowired
    private GuavaCacheUtils guavaCacheUtils;

    @GetMapping("local")
    public String testLocalCache(@RequestParam("id") int id) {
        User cache = (User)guavaCacheUtils.getCache(CacheTypeEnums.USER_CACHE, id);
        return "Success";
    }
}
