package com.aivinog1.cardpay.parse.impl;

import com.aivinog1.cardpay.convert.SupportedFileType;
import com.aivinog1.cardpay.parse.ConverterService;
import com.aivinog1.cardpay.convert.Response;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author aivinog1
 * @since 06.09.2019
 */
@Service
public class JsonConverterService implements ConverterService {
    /**
     * @param file - a file that we need to read
     * @return future that contains list of the responses.
     * @todo #4:30m Let's implement this method. This method should return List of the future responses readed from JSONs.
     */
    @Override
    public CompletableFuture<List<Response>> convert(CompletableFuture<File> file) {
        return CompletableFuture.completedFuture(
                Collections.singletonList(new Response(1L, 1000L, "comment", "file.json", 1L, "OK")));
    }

    @Override
    public SupportedFileType type() {
        return SupportedFileType.JSON;
    }
}
