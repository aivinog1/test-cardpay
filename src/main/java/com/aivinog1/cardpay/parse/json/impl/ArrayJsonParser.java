package com.aivinog1.cardpay.parse.json.impl;

import com.aivinog1.cardpay.convert.JsonType;
import com.aivinog1.cardpay.convert.Request;
import com.aivinog1.cardpay.convert.Response;
import com.aivinog1.cardpay.convert.impl.JacksonRequest;
import com.aivinog1.cardpay.parse.json.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ArrayJsonParser implements JsonParser {

    private final ObjectMapper objectMapper;

    @Override
    public List<Response> parsed(List<String> lines, String fileName) {
        List<Request> requests;
        try {
            requests = objectMapper.readValue(lines.stream().collect(Collectors.joining(System.lineSeparator())),
                    new TypeReference<List<JacksonRequest>>() {
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return requests
                .parallelStream()
                .map(jacksonRequest -> new Response(jacksonRequest.getOrderId(), jacksonRequest.getAmount(), jacksonRequest.getComment(), fileName, 0L, "OK"))
                .collect(Collectors.toList());
    }

    @Override
    public JsonType getType() {
        return JsonType.ARRAY;
    }
}
