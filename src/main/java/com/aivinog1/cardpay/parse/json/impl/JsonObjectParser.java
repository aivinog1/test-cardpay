package com.aivinog1.cardpay.parse.json.impl;

import com.aivinog1.cardpay.convert.JsonContainer;
import com.aivinog1.cardpay.convert.JsonRequest;
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
    public CompletableFuture<JsonContainer> parseJsonString(String jsonString, Long lineNumber) {
        final JsonRequest jsonRequest;
        try {
            jsonRequest = objectMapper.readValue(jsonString, JsonRequest.class);
        } catch (IOException e) {
            // @todo #29:30m Let's create dedicated exception for this. We need it to complete an error processing.
            throw new RuntimeException(e);
        }
        return CompletableFuture.completedFuture(new JsonContainer(jsonRequest, lineNumber));
    }
}
