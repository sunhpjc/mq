package com.sunhp.activiti.we_chat_inter.intf_test.annotation_test_ZhengTi.mdc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.hutool.core.util.IdUtil;

/**
 * @author sunhp
 * @Description mdc拦截器
 * @Date 2022/11/9 11:38
 **/
@Component
public class MdcLogInterceptor implements HandlerInterceptor {
    private static final String TRACE_ID = "trace_id";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //如果有上层调用就用上层的traceId
        String traceId = request.getHeader(TRACE_ID);
        if (StringUtils.isEmpty(traceId)) {
            traceId = this.generateTraceId();
        }
        MDC.put(TRACE_ID, traceId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //调用结束后删除
        MDC.remove(TRACE_ID);
    }

    /**
     * 生成唯一流水号
     *
     * @return
     */
    private String generateTraceId() {
        return IdUtil.getSnowflake()
            .nextIdStr();
    }
}
