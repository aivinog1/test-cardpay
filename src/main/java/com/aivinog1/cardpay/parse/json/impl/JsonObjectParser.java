package com.aivinog1.cardpay.parse.json.impl;

import com.aivinog1.cardpay.convert.Request;
import com.aivinog1.cardpay.convert.RequestContainer;
import com.aivinog1.cardpay.convert.impl.JacksonRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static com.aivinog1.cardpay.configuration.AppConfiguration.JSON_PARSER_EXECUTOR;

@Component
@RequiredArgsConstructor
public class JsonObjectParser {

    private final ObjectMapper objectMapper;

    @Async(JSON_PARSER_EXECUTOR)
    public CompletableFuture<RequestContainer> parseJsonString(String jsonString, Long lineNumber) {
        final Request jacksonRequest;
        try {
            jacksonRequest = objectMapper.readValue(jsonString, JacksonRequest.class);
        } catch (IOException e) {
            // @todo #29:30m Let's create dedicated exception for this. We need it to complete an error processing.
            throw new RuntimeException(e);
        }
        return CompletableFuture.completedFuture(new RequestContainer(jacksonRequest, lineNumber));
    }
}
