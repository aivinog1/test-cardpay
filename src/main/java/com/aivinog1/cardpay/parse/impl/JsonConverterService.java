package com.aivinog1.cardpay.parse.impl;

import com.aivinog1.cardpay.convert.SupportedFileType;
import com.aivinog1.cardpay.parse.ConverterService;
import com.aivinog1.cardpay.convert.Response;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
     * @todo #3:30m Let's remove this method and all of it's usages. It must be replaced by {@link JsonConverterService#convert(Path)}
     */
    @Override
    public CompletableFuture<List<Response>> convert(CompletableFuture<Path> file) {
        try {
            return CompletableFuture.completedFuture(convert(file.get()));
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

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
