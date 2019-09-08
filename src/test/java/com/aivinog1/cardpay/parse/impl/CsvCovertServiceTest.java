package com.aivinog1.cardpay.parse.impl;

import com.aivinog1.cardpay.convert.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CsvCovertServiceTest {

    @Autowired
    private CsvCovertService csvCovertService;

    @Test
    public void successTest() {
        final Path testFile = Paths.get("src/test/resources/input/SuccessMultiLine.csv");
        final List<Response> responses = csvCovertService.convert(testFile);
        assertEquals(5, responses.size());
    }
}