package com.aivinog1.cardpay.cli;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.ExecutionException;

/**
 * @author aivinog1
 * @since 06.09.2019
 */
@Component
public class Runner implements CommandLineRunner {

    private final ExecutionService executionService;
    private final ObjectMapper objectMapper;
    private final Logger logger;

    @Autowired
    public Runner(ExecutionService executionService, ObjectMapper objectMapper) {
        this.executionService = executionService;
        this.objectMapper = objectMapper;
        this.logger = LoggerFactory.getLogger(Runner.class);
    }

    public Runner(ExecutionService executionService, ObjectMapper objectMapper, Logger logger) {
        this.executionService = executionService;
        this.objectMapper = objectMapper;
        this.logger = logger;
    }

    @Override
    public void run(String... args) throws Exception {
        if (args == null || args.length == 0) {
            logger.error("Needs to specify file paths as parameters to this app. Abort.");
            return;
        }
        executionService.parseFiles(args)
                .map(listCompletableFuture -> {
                    try {
                        return listCompletableFuture.get();
                    } catch (InterruptedException | ExecutionException e) {
                        // @todo #14:15m Let's create dedicate exception for this. Our code must be clear from RuntimeExceptions.
                        throw new RuntimeException(e);
                    }
                })
                .flatMap(Collection::parallelStream)
                .forEach(response -> {
                    try {
                        logger.info(objectMapper.writeValueAsString(response));
                    } catch (JsonProcessingException e) {
                        // @todo #14:15m Let's create dedicate exception for this. Our code must be clear from RuntimeExceptions.
                        throw new RuntimeException(e);
                    }
                });
    }
}
