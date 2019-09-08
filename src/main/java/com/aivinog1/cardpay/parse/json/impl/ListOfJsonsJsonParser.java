package com.aivinog1.cardpay.parse.json.impl;

import com.aivinog1.cardpay.convert.JsonType;
import com.aivinog1.cardpay.convert.Request;
import com.aivinog1.cardpay.convert.RequestContainer;
import com.aivinog1.cardpay.convert.Response;
import com.aivinog1.cardpay.parse.json.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ListOfJsonsJsonParser implements JsonParser {

    private final JsonObjectParser jsonObjectParser;

    @Override
    public List<Response> parsed(List<String> lines, String fileName) {
        final List<CompletableFuture<RequestContainer>> parsedJsons = new ArrayList<>(lines.size());
        for (int i = 0; i < lines.size(); i++) {
            final String line = lines.get(i);
            parsedJsons.add(jsonObjectParser.parseJsonString(line, (long) i));
        }
        return parsedJsons
                .parallelStream()
                .map(parsedJsonsFuture -> {
                    try {
                        return parsedJsonsFuture.get();
                    } catch (InterruptedException | ExecutionException e) {
                        // @todo #29:30m Let's implement error handling there. For now we just rethrowing a RuntimeException with an exception.
                        throw new RuntimeException(e);
                    }
                })
                .map(requestContainer -> {
                    final Request jacksonRequest = requestContainer.getRequest();
                    return new Response(
                            jacksonRequest.getOrderId(),
                            jacksonRequest.getAmount(),
                            jacksonRequest.getComment(),
                            fileName,
                            requestContainer.getLine(),
                            "OK");
                })
                .collect(Collectors.toList());
    }

    @Override
    public JsonType getType() {
        return JsonType.LIST_OF_OBJECT;
    }
}
