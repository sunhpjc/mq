package com.sunhp.rocketmq.config;

import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.lang.reflect.Method;
import java.util.concurrent.Executors;

/**
 * @author sunhp
 * @Description
 * @Date 2021/1/18 9:36
 **/
@Configuration
public class ScheduleConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        Method[] methods = BatchProperties.Job.class.getMethods();
        int defaultPoolSize = 3;
        int corePoolSize = 1;
        if (methods != null && methods.length > 0) {
            for (Method method : methods) {
                Scheduled annotation = method.getAnnotation(Scheduled.class);
                if (annotation != null) {
                    corePoolSize++;
                }
            }
            if (defaultPoolSize > corePoolSize)
                corePoolSize = defaultPoolSize;
        }
        scheduledTaskRegistrar.setScheduler(Executors.newScheduledThreadPool(corePoolSize));
    }
}
