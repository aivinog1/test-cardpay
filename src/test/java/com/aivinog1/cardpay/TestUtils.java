package com.aivinog1.cardpay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public final class TestUtils {

    private TestUtils() {}

    public static String readToString(final InputStream stream) throws IOException {
        try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))){
            return bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
}
