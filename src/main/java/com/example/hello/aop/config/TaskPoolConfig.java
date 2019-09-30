package com.example.hello.aop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.Executor;

/**
 * 初始化日志线程池
 */
@Configuration
@EnableAsync
public class TaskPoolConfig {

    @Bean("logExecutor")
    public Executor taskExecutor(){
        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
        executor.setPoolSize(5);
        executor.setThreadNamePrefix("logExecutor-");
        return executor;
    }

}
