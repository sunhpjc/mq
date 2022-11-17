package com.sunhp.activiti.we_chat_inter.redis.smsVerify.my_interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.sunhp.activiti.service.impl.RedisUtils;
import com.sunhp.activiti.we_chat_inter.redis.smsVerify.utils.UserHolder;

import lombok.extern.slf4j.Slf4j;

/**
 * @author sunhp
 * @Description 刷新token拦截器
 * @Date 2022/11/15 20:22
 **/
@Component
@Slf4j
public class RefreshTokenInterceptor implements HandlerInterceptor {
    /**
     * 请求头携带token
     */
    private static final String LOGIN_TOKEN = "authorization";

    /**
     * 用户信息key
     */
    private static final String LOGIN_USER_KEY = "loginUser";

    @Autowired
    RedisUtils redisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("请求头获取token");
        String token = request.getHeader(LOGIN_TOKEN);
        if (StringUtils.isEmpty(token)) {
            return true;
        }
        log.info("判断redis中的用户是否存在");
        if (!redisUtils.exists(LOGIN_USER_KEY + token)) {
            return true;
        }
        String userInfo = redisUtils.get(LOGIN_USER_KEY + token);
        log.info("将用户信息保存到ThreadLocal");
        UserHolder.saveUser(userInfo);
        log.info("刷新token有效时间");
        redisUtils.expire(LOGIN_USER_KEY + token, 300);
        //放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
        Exception ex) {
        log.info("刷新token拦截器释放user,防止内存泄露");
        UserHolder.removeUser();
    }
}
