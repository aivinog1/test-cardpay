package com.aivinog1.cardpay.parse.impl;

import com.aivinog1.cardpay.convert.Response;
import com.aivinog1.cardpay.convert.SupportedFileType;
import com.aivinog1.cardpay.parse.ConverterService;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

/**
 * @author aivinog1
 * @since 06.09.2019
 */
@Service
public class JsonConverterService implements ConverterService {

    /**
     * @param file - a file that needs to be readed
     * @return List of readed responses.
     * @todo #3:30 Let's implement this method. It must be read file and process it by two cases: a json array and a list of json objects
     */
    @Override
    public List<Response> convert(Path file) {
        return Collections.singletonList(new Response(1L, 1000L, "comment", "file.json", 1L, "OK"));
    }

    @Override
    public SupportedFileType type() {
        return SupportedFileType.JSON;
    }
}
