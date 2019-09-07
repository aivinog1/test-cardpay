package com.aivinog1.cardpay.parse.json.impl;

import com.aivinog1.cardpay.convert.JsonRequest;
import com.aivinog1.cardpay.convert.JsonType;
import com.aivinog1.cardpay.convert.Response;
import com.aivinog1.cardpay.parse.json.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ArrayJsonParser implements JsonParser {

    private final ObjectMapper objectMapper;

    @Override
    public List<Response> parsed(Collection<String> lines) {
        List<JsonRequest> requests;
        try {
            requests = objectMapper.readValue(lines.stream().collect(Collectors.joining(System.lineSeparator())),
                    new TypeReference<List<JsonRequest>>() {
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return requests
                .parallelStream()
                // @todo #30:15m Let's pass to this method the file name. Needs the file name in the response.
                .map(jsonRequest -> new Response(jsonRequest.getOrderId(), jsonRequest.getAmount(), jsonRequest.getComment(), "filename", 0L, "OK"))
                .collect(Collectors.toList());
    }

    @Override
    public JsonType getType() {
        return JsonType.ARRAY;
    }
}
