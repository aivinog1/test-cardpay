package com.aivinog1.cardpay.cli;

import com.aivinog1.cardpay.convert.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.slf4j.Logger;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static com.aivinog1.cardpay.TestUtils.readToString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RunnerTest {

    private final Logger logger = mock(Logger.class);
    private final ExecutionService executionService = mock(ExecutionService.class);
    private final Runner runner = new Runner(executionService, new ObjectMapper(), logger, Collections.emptyList());

    @Test
    public void baseTest() throws Exception {
        final Response response = new Response(1L, 1L, "comment", "filename", 1L, "OK");
        when(executionService.parseFiles(any()))
                .thenReturn(
                        Stream.<CompletableFuture<List<Response>>>builder()
                                .add(CompletableFuture
                                        .completedFuture(Collections.singletonList(response)))
                                .build());
        runner.run("test.json");
        try (final InputStream stream = RunnerTest.class.getResourceAsStream("/response/SuccessResponse.json")) {
            verify(logger, times(1)).info(eq(readToString(stream)));
        }
        verifyNoMoreInteractions(logger);
    }
}
