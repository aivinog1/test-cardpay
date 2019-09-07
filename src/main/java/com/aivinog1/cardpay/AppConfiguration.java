package com.aivinog1.cardpay;

import com.aivinog1.cardpay.convert.SupportedFileType;
import com.aivinog1.cardpay.parse.ConverterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author aivinog1
 * @since 06.09.2019
 */
@Configuration
@EnableAsync
public class AppConfiguration {

    public static final String FILE_TASK_EXECUTOR = "fileTaskExecutor";

    @Bean
    public Map<SupportedFileType, ConverterService> convertersMap(List<ConverterService> converters) {
        return converters.stream().collect(Collectors.toMap(ConverterService::type, Function.identity()));
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    /**
     * @return executor that would be using for running reading tasks
     * @todo #3:30m Let's move configuration of the Executor in configuration files. That gives us more flexibility.
     */
    @Bean(FILE_TASK_EXECUTOR)
    public Executor fileTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(25);
        executor.setQueueCapacity(30);
        executor.setThreadNamePrefix("FileReading-");
        return executor;
    }
}
