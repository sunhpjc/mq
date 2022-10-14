package com.sunhp.activiti.we_chat_inter.annotations_interception;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sunhp.activiti.enums.ResponseCodeEnum;
import com.sunhp.activiti.exception.MyException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author sunhp
 * @Description 外部接口切面
 * @Date 2022/10/14 10:23
 **/
@Aspect
@Component
@Slf4j
public class OnlyOutAccessAspect {
    private static final String FROM_SOURCE = "from_source";
    private static final String FROM_VALUE = "public";

    @Pointcut("@within(com.sunhp.activiti.we_chat_inter.annotations_interception.OnlyOutAccess)")
    public void onlyOutAccessOnClass(){}

    @Pointcut("@annotation(com.sunhp.activiti.we_chat_inter.annotations_interception.OnlyOutAccess)")
    public void onlyOutAccessOnMethod(){}

    @Before(value = "onlyOutAccessOnClass() || onlyOutAccessOnMethod()")
    public void before(){
        HttpServletRequest request
            = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String fromSource = request.getHeader(FROM_SOURCE);
        if(StringUtils.isNotEmpty(fromSource) && FROM_VALUE.equals(fromSource)){
            log.error("该接口只允许内部调用");
            throw new MyException(ResponseCodeEnum.FAILED.getCode(), "该接口只允许内部调用");
        }
    }
}
