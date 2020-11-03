package com.example.spring.enable.task;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @description: 自动装配
 * @author: huss
 * @time: 2020/10/21 16:17
 */
public class EnableTaskAutoConfiguration {

    @ConditionalOnMissingBean(TaskExecutor.class)
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 核心线程数
        taskExecutor.setCorePoolSize(5);
        // 最大线程数
        taskExecutor.setMaxPoolSize(50);
        // 队列最大长度
        taskExecutor.setQueueCapacity(1000);
        // 线程池维护线程所允许的空闲时间(单位秒)
        taskExecutor.setKeepAliveSeconds(120);
        // 线程池对拒绝任务(无线程可用)的处理策略 ThreadPoolExecutor.CallerRunsPolicy策略 ,调用者的线程会执行该任务,如果执行器已关闭,则丢弃.
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());

        return taskExecutor;
    }

}
