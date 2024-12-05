package com.sunhp.activiti.we_chat_inter.intf_test.annotation_test_ZhengTi.mdc.config;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.slf4j.MDC;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author sunhp
 * @Description mdc重写线程池
 * @Date 2024/10/8 15:37
 **/
public class ThreadPoolExecutorMdcWrapper extends ThreadPoolTaskExecutor {
    private static final long serialVersionUID = 3940722618853093830L;

    @Override
    public void execute(Runnable task) {
        super.execute(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
    }
}
