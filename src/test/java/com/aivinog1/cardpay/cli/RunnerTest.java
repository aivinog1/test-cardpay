package com.aivinog1.cardpay.cli;

import com.aivinog1.cardpay.convert.Response;
import com.aivinog1.cardpay.convert.SupportedFileType;
import com.aivinog1.cardpay.parse.ConverterService;
import com.aivinog1.cardpay.parse.impl.JsonConverterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RunnerTest {

    private final Map<SupportedFileType, ConverterService> converterServiceMap = new HashMap<>();

    private final JsonConverterService jsonConverterService = mock(JsonConverterService.class);

    {
        converterServiceMap.put(SupportedFileType.JSON, jsonConverterService);
    }

    private final Logger logger = mock(Logger.class);
    private final Runner runner = new Runner(converterServiceMap, new ObjectMapper(), logger);

    @Test
    public void baseTest() throws Exception {
        when(jsonConverterService.type()).thenReturn(SupportedFileType.JSON);
        final Response response = new Response(1L, 1L, "comment", "filename", 1L, "OK");
        when(jsonConverterService.convert(any()))
                .thenReturn(CompletableFuture.completedFuture(
                Collections.singletonList(response)));
        runner.run();
        try (final InputStream stream = RunnerTest.class.getResourceAsStream("/response/SuccessResponse.json")){
            verify(logger, times(1)).info(eq(readToString(stream)));
        }
        verifyNoMoreInteractions(logger);
    }

    private String readToString(final InputStream stream) throws IOException {
        try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))){
            return bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
}
