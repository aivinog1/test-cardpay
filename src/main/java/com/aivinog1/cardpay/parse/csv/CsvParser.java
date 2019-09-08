package com.aivinog1.cardpay.parse.csv;

import com.aivinog1.cardpay.convert.Request;
import com.aivinog1.cardpay.convert.RequestContainer;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static com.aivinog1.cardpay.configuration.AppConfiguration.CSV_PARSER_EXECUTOR;

@Component
@RequiredArgsConstructor
public class CsvParser {

    private final ObjectReader csvRequestReader;

    @Async(CSV_PARSER_EXECUTOR)
    public CompletableFuture<RequestContainer> parseCsv(String line, Long lineNumber) {
        final Request request;
        try {
            request = csvRequestReader.readValue(line);
        } catch (IOException e) {
            // @todo #2:15m Let's properly handle this exception. For now it's just rethrowing such exception.
            throw new RuntimeException(e);
        }
        return CompletableFuture.completedFuture(new RequestContainer(request, lineNumber));
    }
}
