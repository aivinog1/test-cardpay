package com.aivinog1.cardpay.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ConfigurationProperties("cardplay.test")
@Data
@Validated
public class AppConfigurationProperties {

    @NotNull
    private Executors executors;

    @Data
    @Validated
    public static class Executors {
        @NotNull
        private Executor fileTaskExecutor;
        @NotNull
        private Executor executorServiceExecutor;
        @NotNull
        private Executor jsonParserExecutor;
    }

    @Data
    @Validated
    public static class Executor {
        @NotNull
        private Integer corePoolSize;
        @NotNull
        private Integer maxPoolSize;
        @NotNull
        private Integer queueCapacity;
        @NotEmpty
        private String threadNamePrefix;
    }
}
