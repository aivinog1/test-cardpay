package com.aivinog1.cardpay.parse.json.impl;

import com.aivinog1.cardpay.TestUtils;
import com.aivinog1.cardpay.convert.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArrayJsonParserTest {

    @Autowired
    private ArrayJsonParser arrayJsonParser;

    @Test
    public void testParsedSuccess() throws IOException {
        final List<String> strings;
        final String pathToJson = "/input/SuccessArray.json";
        try (final InputStream stream = ArrayJsonParserTest.class.getResourceAsStream(pathToJson)) {
            strings = TestUtils.readToStrings(stream);
        }
        final List<Response> parsed = arrayJsonParser.parsed(strings, pathToJson);
        assertEquals(parsed.size(), 4);
        final Response response0 = parsed.get(0);
        assertEquals(Long.valueOf(2L), response0.getId());
        assertEquals("Check, please!", response0.getComment());
    }
}