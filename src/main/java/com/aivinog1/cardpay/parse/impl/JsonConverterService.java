package com.aivinog1.cardpay.parse.impl;

import com.aivinog1.cardpay.convert.JsonType;
import com.aivinog1.cardpay.convert.Response;
import com.aivinog1.cardpay.convert.SupportedFileType;
import com.aivinog1.cardpay.parse.ConverterService;
import com.aivinog1.cardpay.parse.json.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author aivinog1
 * @since 06.09.2019
 */
@Service
@RequiredArgsConstructor
public class JsonConverterService implements ConverterService {

    private final Map<JsonType, JsonParser> parserMap;

    @Override
    public List<Response> convert(Path file) {
        final List<String> lines;
        try {
            lines = Files.readAllLines(file);
        } catch (IOException e) {
            // @todo #18:30m Let's create dedicated exception for this. If we can't read lines, than we can't process further.
            throw new RuntimeException(e);
        }

        if (lines.size() == 0) {
            // @todo #18:30m Let's create dedicated exception for this. If a file is empty we can't process further.
            throw new RuntimeException();
        }

        final String firstLine = lines.get(0);
        if (firstLine.contains("[")) {
            // @todo #18:30m needs a dedicated exception for this. If we can't find an array element we can't process further.
            final JsonParser jsonParser = Optional.ofNullable(parserMap.get(JsonType.ARRAY)).orElseThrow(RuntimeException::new);
            return jsonParser.parsed(lines);
        } else if (firstLine.contains("{")) {
            // @todo #18:30m needs a dedicated exception for this. If we can't find an list of object element we can't process further.
            final JsonParser jsonParser = Optional.ofNullable(parserMap.get(JsonType.LIST_OF_OBJECT)).orElseThrow(RuntimeException::new);
            return jsonParser.parsed(lines);
        } else {
            // @todo #18:30m needs to improve this. If a file contains many empty strings before - this doesn't works.
            throw new UnsupportedOperationException("Can't find proper json type for this file.");
        }
    }

    @Override
    public SupportedFileType type() {
        return SupportedFileType.JSON;
    }
}
