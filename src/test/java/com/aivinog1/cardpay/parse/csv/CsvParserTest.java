package com.aivinog1.cardpay.parse.csv;

import com.aivinog1.cardpay.TestUtils;
import com.aivinog1.cardpay.convert.Request;
import com.aivinog1.cardpay.convert.RequestContainer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CsvParserTest {

    @Autowired
    private CsvParser csvParser;

    @Test
    public void testSuccess() throws IOException, ExecutionException, InterruptedException {
        final List<String> strings;
        try (final InputStream stream = CsvParserTest.class.getResourceAsStream("/input/SuccessSingleLine.csv")) {
            strings = TestUtils.readToStrings(stream);
        }
        assertEquals(1, strings.size());
        final String line = strings.get(0);
        final CompletableFuture<RequestContainer> future = csvParser.parseCsv(line, 0L);
        final RequestContainer requestContainer = future.get();
        assertEquals(Long.valueOf(0L), requestContainer.getLine());
        final Request request = requestContainer.getRequest();
        assertEquals(Long.valueOf(1L), request.getOrderId());
    }
}