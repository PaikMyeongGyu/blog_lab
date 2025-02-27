package com.search.search.board.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class BoardAsyncConfig {
    private static final int CORE_POOL_SIZE = 100;
    private static final int MAX_POOL_SIZE = 500;
    private static final int QUEUE_CAPACITY = 200;

    @Bean(name = "boardExecutor")
    public Executor scheduleExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setThreadNamePrefix("boardExecutor-");
        executor.initialize();
        return executor;
    }
}
