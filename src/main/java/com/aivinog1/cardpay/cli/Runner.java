package com.aivinog1.cardpay.cli;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.concurrent.ExecutorConfigurationSupport;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.io.PrintStream;
import java.util.Collection;
import java.util.List;
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
    private final List<ThreadPoolTaskExecutor> executors;
    private final PrintStream printStream;

    @Autowired
    public Runner(ExecutionService executionService, ObjectMapper objectMapper, List<ThreadPoolTaskExecutor> executors, PrintStream printStream) {
        this.executionService = executionService;
        this.objectMapper = objectMapper;
        this.executors = executors;
        this.printStream = printStream;
        this.logger = LoggerFactory.getLogger(Runner.class);
    }

    public Runner(ExecutionService executionService, ObjectMapper objectMapper, Logger logger, List<ThreadPoolTaskExecutor> executors, PrintStream printStream) {
        this.executionService = executionService;
        this.objectMapper = objectMapper;
        this.logger = logger;
        this.executors = executors;
        this.printStream = printStream;
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
                        // @todo #14:15m Let's create dedicate exception for this. And think about exception handling. Our code must be clear from RuntimeExceptions.
                        throw new RuntimeException(e);
                    }
                })
                .flatMap(Collection::parallelStream)
                .forEach(response -> {
                    try {
                        printStream.println(objectMapper.writeValueAsString(response));
                    } catch (JsonProcessingException e) {
                        // @todo #14:15m Let's create dedicate exception for this. Our code must be clear from RuntimeExceptions.
                        throw new RuntimeException(e);
                    }
                });
        executors.forEach(ExecutorConfigurationSupport::shutdown);
    }
}
