package com.sunhp.activiti.we_chat_inter.intf_test.annotation_test_ZhengTi.mdc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sunhp.activiti.we_chat_inter.intf_test.annotation_test_ZhengTi.mdc.MdcLogInterceptor;

/**
 * @author sunhp
 * @Description 配置拦截器
 * @Date 2022/11/9 15:27
 **/
@Configuration
public class MdcWebConfig implements WebMvcConfigurer {

    @Autowired
    private MdcLogInterceptor mdcLogInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(mdcLogInterceptor)
            .addPathPatterns("/**")
            //排出接口 "/","/login","/css/**","/fonts/**","/images/**","/js/**"等
            /*.excludePathPatterns()*/;
    }
}
