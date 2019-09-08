package com.aivinog1.cardpay.parse.impl;

import com.aivinog1.cardpay.convert.Request;
import com.aivinog1.cardpay.convert.RequestContainer;
import com.aivinog1.cardpay.convert.Response;
import com.aivinog1.cardpay.convert.SupportedFileType;
import com.aivinog1.cardpay.parse.ConverterService;
import com.aivinog1.cardpay.parse.csv.CsvParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CsvCovertService implements ConverterService {

    private final CsvParser csvParser;

    @Override
    public List<Response> convert(Path file) {
        final List<String> lines;
        try {
            lines = Files.readAllLines(file);
        } catch (IOException e) {
            // @todo #2:15m Let's process this exception later. For now it's just rethrowing RuntimeException
            throw new RuntimeException(e);
        }
        final List<CompletableFuture<RequestContainer>> containers = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            containers.add(csvParser.parseCsv(lines.get(i), (long) i));
        }
        return containers
                .parallelStream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        // @todo #2:15m We need to process this exception later. For now it's just rethrowing.
                        throw new RuntimeException(e);
                    }
                })
                .map(requestContainer -> {
                    final Request request = requestContainer.getRequest();
                    return new Response(
                            request.getOrderId(),
                            request.getAmount(),
                            request.getComment(),
                            file.toString(),
                            requestContainer.getLine(),
                            "OK");
                })
                .collect(Collectors.toList());
    }

    @Override
    public SupportedFileType type() {
        return SupportedFileType.CSV;
    }
}
