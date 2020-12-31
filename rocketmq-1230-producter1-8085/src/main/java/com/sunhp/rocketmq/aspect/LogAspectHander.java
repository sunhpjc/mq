package com.sunhp.rocketmq.aspect;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author sunhp
 * @Description
 * @Date 2020/12/31 16:29
 **/
@Aspect
@Component
public class LogAspectHander {
    private static final Logger logger = LoggerFactory.getLogger(LogAspectHander.class);
    /*
     * 定义一个切面，拦截包和包下面的所有方法,*后面空格一定要有
     * */
    @Pointcut("execution(* com.sunhp.rocketmq.controller..*.*(..))")
    public void logPointCut(){

    }
    /**
     * 在上面定义的切面方法之前执行该方法
     * @param joinPoint jointPoint
     */
    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint) {
//        logger.info("====doBefore方法进入了====");
        // 获取签名
        Signature signature = joinPoint.getSignature();
        // 获取切入的包名
        String declaringTypeName = signature.getDeclaringTypeName();
        // 获取即将执行的方法名
        String funcName = signature.getName();
//        logger.info("即将执行方法为: {}，属于{}包", funcName, declaringTypeName);

        // 也可以用来记录一些信息，比如获取请求的url和ip
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 获取请求url
        String url = request.getRequestURL().toString();
        // 获取请求ip
        String ip = request.getRemoteAddr();
        String servletPath = request.getServletPath();
        //参数
        String param = JSONObject.toJSONString(joinPoint.getArgs());
//        logger.info("@@@@@@Before url为：{}，ip地址为：{},参数：{}", url, ip,param);
        logger.info("@@@@@@Before 接口为：{},请求参数：{}", servletPath,param);
    }
    /**
     * 在上面定义的切面方法之后执行该方法
     * @param joinPoint jointPoint
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "response")
    public void doAfter(JoinPoint joinPoint,Object response) {

//        logger.info("====doAfter方法进入了====");
        Signature signature = joinPoint.getSignature();
        String method = signature.getName();
//        logger.info("方法{}已经执行完", method);

        // 也可以用来记录一些信息，比如获取请求的url和ip
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 获取请求url
        String url = request.getRequestURL().toString();
        // 获取请求ip
        String ip = request.getRemoteAddr();
        String servletPath = request.getServletPath();
        //参数
        String param = JSONObject.toJSONString(response);
//        logger.info("@@@@@@After url为：{},响应参数：{}",url,param);
        logger.info("@@@@@@After 接口为：{},响应参数：{}",servletPath,param);
    }
}
