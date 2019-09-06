package com.aivinog1.cardpay.cli;

import com.aivinog1.cardpay.convert.Response;
import com.aivinog1.cardpay.convert.SupportedFileType;
import com.aivinog1.cardpay.parse.ConverterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author aivinog1
 * @since 06.09.2019
 */
@Component
public class Runner implements CommandLineRunner {

    private final Map<SupportedFileType, ConverterService> converterServiceMap;
    private final ObjectMapper objectMapper;
    private final Logger logger;

    @Autowired
    public Runner(Map<SupportedFileType, ConverterService> converterServiceMap, ObjectMapper objectMapper) {
        this.converterServiceMap = converterServiceMap;
        this.objectMapper = objectMapper;
        this.logger = LoggerFactory.getLogger(Runner.class);
    }

    public Runner(Map<SupportedFileType, ConverterService> converterServiceMap, ObjectMapper objectMapper, Logger logger) {
        this.converterServiceMap = converterServiceMap;
        this.objectMapper = objectMapper;
        this.logger = logger;
    }

    @Override
    public void run(String... args) throws Exception {
        final ConverterService converterService = converterServiceMap.get(SupportedFileType.JSON);
        final File tempFile = File.createTempFile("test", "test");
        final CompletableFuture<List<Response>> convertResult = converterService.convert(CompletableFuture.completedFuture(tempFile));
        final List<Response> responses = convertResult.get();
        for (final Response response : responses) {
            logger.info(objectMapper.writeValueAsString(response));
        }
    }
}
