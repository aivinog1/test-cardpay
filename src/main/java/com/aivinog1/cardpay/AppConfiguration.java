package com.aivinog1.cardpay;

import com.aivinog1.cardpay.convert.SupportedFileType;
import com.aivinog1.cardpay.parse.ConverterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author aivinog1
 * @since 06.09.2019
 */
@Configuration
public class AppConfiguration {

    @Bean
    public Map<SupportedFileType, ConverterService> convertersMap(List<ConverterService> converters) {
        return converters.stream().collect(Collectors.toMap(ConverterService::type, Function.identity()));
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
