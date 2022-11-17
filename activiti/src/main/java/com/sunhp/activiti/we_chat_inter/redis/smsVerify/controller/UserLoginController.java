package com.sunhp.activiti.we_chat_inter.redis.smsVerify.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunhp.activiti.enums.ResponseCodeEnum;
import com.sunhp.activiti.vo.ResultVO;
import com.sunhp.activiti.we_chat_inter.redis.smsVerify.enums.LoginTypeEnum;
import com.sunhp.activiti.we_chat_inter.redis.smsVerify.pojo.dto.LoginFormDto;
import com.sunhp.activiti.we_chat_inter.redis.smsVerify.pojo.vo.LoginFormVo;
import com.sunhp.activiti.we_chat_inter.redis.smsVerify.service.SmsVerifyService;

import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author sunhp
 * @Description
 * @Date 2022/11/15 21:35
 **/
@RestController
@RequestMapping("/user")
@Slf4j
public class UserLoginController {
    @Autowired
    SmsVerifyService smsVerifyService;

    @PostMapping("/login")
    public ResultVO login(@Valid LoginFormVo loginFormVo) {
        LoginFormDto loginFormDto = new LoginFormDto();
        BeanUtil.copyProperties(loginFormVo, loginFormDto, false);
        loginFormDto.setLoginTypeEnum(LoginTypeEnum.find(loginFormVo.getLoginType()));
        ResultVO resultVO = smsVerifyService.login(loginFormDto);
        return resultVO;
    }

    @PostMapping("/sendCode")
    public ResultVO sendCode(String phone) {
        ResultVO resultVO = smsVerifyService.sendCode(phone);
        return resultVO;
    }

    @PostMapping("/list")
    public ResultVO list() {
        log.info("测试获取list");
        return new ResultVO(ResponseCodeEnum.SUCCESS);
    }
}
