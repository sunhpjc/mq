package com.sunhp.activiti.we_chat_inter.redis.smsVerify.my_interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.sunhp.activiti.enums.ResponseCodeEnum;
import com.sunhp.activiti.exception.MyException;
import com.sunhp.activiti.we_chat_inter.redis.smsVerify.utils.UserHolder;

import lombok.extern.slf4j.Slf4j;

/**
 * @author sunhp
 * @Description 登录拦截
 * @Date 2022/11/15 21:26
 **/
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (null == UserHolder.getUser()) {
            throw new MyException(ResponseCodeEnum.LOGIN_ERROR.getCode(), ResponseCodeEnum.LOGIN_ERROR.getMsg());
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
        Exception ex) {
        log.info("登录拦截移除用户，防止内存泄露");
        UserHolder.removeUser();
    }
}
