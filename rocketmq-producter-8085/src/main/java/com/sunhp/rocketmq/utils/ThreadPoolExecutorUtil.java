package com.sunhp.rocketmq.utils;

import com.sunhp.rocketmq.enums.ResponseCodeEnum;
import com.sunhp.rocketmq.exception.MyException;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static ch.qos.logback.core.CoreConstants.CORE_POOL_SIZE;

/**
 * @Description
 * @Date 2021/1/5 14:24
 **/
@Service(value = "threadPoolExecutorUtil")
public class ThreadPoolExecutorUtil {
    ThreadPoolExecutor executor = null;
    private static final int MAX_POOL_SIZE = 5;
    private static final int QUEUE_CAPACITY = 100;
    private static final Long KEEP_ALIVE_TIME = 1L;

    public synchronized ThreadPoolExecutor getInstance(){
        executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                new ThreadPoolExecutor.CallerRunsPolicy());
        if(executor != null){
            return executor;
        }
        else {
            throw new MyException(ResponseCodeEnum.NULLPOINT.getCode(),"线程池对象实例化失败");
        }
    }
}
