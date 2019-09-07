package com.aivinog1.cardpay.parse;

import com.aivinog1.cardpay.convert.Response;
import com.aivinog1.cardpay.convert.SupportedFileType;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author aivinog1
 * @since 06.09.2019
 */
public interface ConverterService {

    CompletableFuture<List<Response>> convert(CompletableFuture<Path> file);

    List<Response> convert(Path file);

    SupportedFileType type();
}
