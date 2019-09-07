package com.aivinog1.cardpay.file;

import com.aivinog1.cardpay.exception.file.FileIsNotReadableException;
import com.aivinog1.cardpay.exception.file.FileNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

import static com.aivinog1.cardpay.AppConfiguration.FILE_TASK_EXECUTOR;

@Component
@Slf4j
public class FileReader {

    @Async(FILE_TASK_EXECUTOR)
    public CompletableFuture<Path> readFile(String filePath) {
        log.trace("Start reading: {}", filePath);
        final Path path = Paths.get(filePath);
        log.trace("Start checking path: {}", path);
        checkFoundPath(path);
        return CompletableFuture.completedFuture(path);
    }

    private void checkFoundPath(Path path) {
        if (!Files.exists(path)) {
            throw new FileNotFoundException(path.toString());
        }
        if (!Files.isReadable(path)) {
            throw new FileIsNotReadableException(path.toString());
        }
    }

}
