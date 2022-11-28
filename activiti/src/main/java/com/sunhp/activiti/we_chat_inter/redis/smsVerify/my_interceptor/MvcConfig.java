package com.sunhp.activiti.we_chat_inter.redis.smsVerify.my_interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author sunhp
 * @Description 添加拦截器
 * @Date 2022/11/15 21:31
 **/
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    // @Autowired
    // LoginInterceptor loginInterceptor;
    // @Autowired
    // RefreshTokenInterceptor refreshTokenInterceptor;
    //
    // @Override
    // public void addInterceptors(InterceptorRegistry registry) {
    //     registry.addInterceptor(loginInterceptor)
    //         .excludePathPatterns("/user/login","/user/sendCode")
    //         .order(1);
    //     registry.addInterceptor(refreshTokenInterceptor)
    //         .addPathPatterns("/**")
    //         .order(0);
    // }
}
