package com.aivinog1.cardpay.file;

import com.aivinog1.cardpay.convert.SupportedFileType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    private final FileReader fileReader;

    public Stream<CompletableFuture<Path>> getFiles(String[] paths) {
        return Arrays.stream(paths)
                .parallel()
                .map(fileReader::readFile);
    }

    public SupportedFileType getFileTypeOfTheFile(Path path) {
        log.trace("Start determine SupportedFileType for: {}", path);
        try {
            final String contentType = Files.probeContentType(path);
            final SupportedFileType fileType = SupportedFileType.getByMimeType(contentType);
            log.trace("Getting probeContentType successfully for: {}. It is: {}", path, fileType);
            return fileType;
        } catch (Exception e) {
            log.trace("Getting probeContentType is not successful for: {}. " +
                    "Exception is: {}. Start determine by extension", path, e.getMessage(), e);
            final SupportedFileType fileType = SupportedFileType.getByExtension(getExtension(path.toString()));
            log.trace("Successfully getting by extension for: {}. It is: {}", path, fileType);
            return fileType;
        }
    }

    private String getExtension(String fileName) {
        String extension = "";

        int i = fileName.lastIndexOf('.');
        int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

        if (i > p) {
            extension = fileName.substring(i + 1);
        }
        return extension;
    }
}
