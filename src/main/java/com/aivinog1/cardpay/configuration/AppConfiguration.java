package com.aivinog1.cardpay.configuration;

import com.aivinog1.cardpay.convert.JsonType;
import com.aivinog1.cardpay.convert.SupportedFileType;
import com.aivinog1.cardpay.convert.impl.JacksonRequest;
import com.aivinog1.cardpay.parse.ConverterService;
import com.aivinog1.cardpay.parse.json.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.validation.constraints.NotNull;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author aivinog1
 * @since 06.09.2019
 */
@Configuration
@EnableConfigurationProperties(AppConfigurationProperties.class)
@EnableAsync
public class AppConfiguration {

    public static final String FILE_TASK_EXECUTOR = "fileTaskExecutor";
    public static final String EXECUTOR_SERVICE_EXECUTOR = "executorServiceExecutor";
    public static final String JSON_PARSER_EXECUTOR = "jsonParserExecutor";
    public static final String CSV_PARSER_EXECUTOR = "csvParserExecutor";

    @Bean
    public Map<SupportedFileType, ConverterService> convertersMap(List<ConverterService> converters) {
        return converters.stream().collect(Collectors.toMap(ConverterService::type, Function.identity()));
    }

    @Bean
    public Map<JsonType, JsonParser> parsersMap(List<JsonParser> jsonParsers) {
        return jsonParsers.stream().collect(Collectors.toMap((JsonParser::getType), Function.identity()));
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public CsvMapper csvMapper() {
        return new CsvMapper();
    }

    @Bean
    public ObjectReader csvRequestReader(CsvMapper csvMapper) {
        return csvMapper.readerWithSchemaFor(JacksonRequest.class);
    }

    @Bean
    public PrintStream outputStream() {
        return System.out;
    }

    @Bean(FILE_TASK_EXECUTOR)
    public ThreadPoolTaskExecutor fileTaskExecutor(AppConfigurationProperties appConfigurationProperties) {
        final AppConfigurationProperties.@NotNull Executor fileTaskExecutorConfig = appConfigurationProperties.getExecutors().getFileTaskExecutor();
        return getThreadPoolTaskExecutor(fileTaskExecutorConfig);
    }

    @Bean(EXECUTOR_SERVICE_EXECUTOR)
    public ThreadPoolTaskExecutor executorServiceExecutor(AppConfigurationProperties appConfigurationProperties) {
        final AppConfigurationProperties.@NotNull Executor executorServiceExecutorConfig = appConfigurationProperties.getExecutors().getExecutorServiceExecutor();
        return getThreadPoolTaskExecutor(executorServiceExecutorConfig);
    }

    @Bean(JSON_PARSER_EXECUTOR)
    public ThreadPoolTaskExecutor jsonParserExecutor(AppConfigurationProperties appConfigurationProperties) {
        final AppConfigurationProperties.@NotNull Executor jsonParserExecutorConfig = appConfigurationProperties.getExecutors().getJsonParserExecutor();
        return getThreadPoolTaskExecutor(jsonParserExecutorConfig);
    }

    @Bean(CSV_PARSER_EXECUTOR)
    public ThreadPoolTaskExecutor csvParserExecutor(AppConfigurationProperties appConfigurationProperties) {
        final AppConfigurationProperties.@NotNull Executor csvParserExecutorConfig = appConfigurationProperties.getExecutors().getCsvParserExecutor();
        return getThreadPoolTaskExecutor(csvParserExecutorConfig);

    }

    private ThreadPoolTaskExecutor getThreadPoolTaskExecutor(AppConfigurationProperties.@NotNull Executor jsonParserExecutorConfig) {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(jsonParserExecutorConfig.getCorePoolSize());
        executor.setMaxPoolSize(jsonParserExecutorConfig.getMaxPoolSize());
        executor.setQueueCapacity(jsonParserExecutorConfig.getQueueCapacity());
        executor.setThreadNamePrefix(jsonParserExecutorConfig.getThreadNamePrefix());
        return executor;
    }
}
