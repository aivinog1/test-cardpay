package com.aivinog1.cardpay.cli;

import com.aivinog1.cardpay.convert.Response;
import com.aivinog1.cardpay.convert.SupportedFileType;
import com.aivinog1.cardpay.file.FileService;
import com.aivinog1.cardpay.parse.ConverterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/**
 * @todo #3:30m Needs to write tests. For now tests are absent for this class, but we need unit tests for this class.
 */
@Service
@RequiredArgsConstructor
public class ExecutionService {

    private final FileService fileService;
    private final Map<SupportedFileType, ConverterService> converterServiceMap;

    public Stream<CompletableFuture<List<Response>>> parseFiles(String... filePaths) {

        return fileService.getFiles(filePaths)
                .map(pathCompletableFuture -> pathCompletableFuture
                        .thenApplyAsync(path -> {
                            final SupportedFileType fileType = fileService.getFileTypeOfTheFile(path);
                            final ConverterService converterService = Optional
                                    .ofNullable(converterServiceMap.get(fileType))
                                    // @todo #3:30m Let's create dedicated exception for this situation. For now it's just runtime exception, but we needs a dedicated exception with reason.
                                    .orElseThrow(() -> new RuntimeException());
                            return converterService.convert(path);
                        }));

    }
}
