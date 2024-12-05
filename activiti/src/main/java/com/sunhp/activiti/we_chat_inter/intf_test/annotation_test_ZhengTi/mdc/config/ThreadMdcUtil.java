package com.sunhp.activiti.we_chat_inter.intf_test.annotation_test_ZhengTi.mdc.config;

import java.util.Map;
import java.util.concurrent.Callable;

import org.slf4j.MDC;

import cn.hutool.core.util.IdUtil;

/**
 * @author sunhp
 * @Description mdc线程池工具
 * @Date 2024/10/8 15:38
 **/
public class ThreadMdcUtil {
    private static final String TRACE_ID = "trace_id";

    public static void setTraceIdIfAbsent() {
        if (MDC.get(TRACE_ID) == null) {
            MDC.put(TRACE_ID, IdUtil.getSnowflake()
                .nextIdStr());
        }
    }

    public static <T> Callable<T> wrap(final Callable<T> callable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            setTraceIdIfAbsent();
            try {
                return callable.call();
            } finally {
                MDC.clear();
            }
        };
    }

    public static Runnable wrap(final Runnable runnable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            //设置traceId
            setTraceIdIfAbsent();
            try {
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }
}
