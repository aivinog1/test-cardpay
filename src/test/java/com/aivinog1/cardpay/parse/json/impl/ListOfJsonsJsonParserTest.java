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
public class ListOfJsonsJsonParserTest {

    @Autowired
    private ListOfJsonsJsonParser listOfJsonsJsonParser;

    @Test
    public void successTest() throws IOException {
        final List<Response> parsed;
        final String pathToJson = "/input/SuccessListOfJson.json";
        try (final InputStream stream = ListOfJsonsJsonParser.class.getResourceAsStream(pathToJson)) {
            parsed = listOfJsonsJsonParser.parsed(TestUtils.readToStrings(stream), pathToJson);
        }
        assertEquals(4, parsed.size());
    }
}