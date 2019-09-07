package com.aivinog1.cardpay.file;

import com.aivinog1.cardpay.convert.SupportedFileType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileServiceTest {

    @Autowired
    private FileService fileService;

    @Test
    public void testFileTypeJsonObject() {
        testPath("src/test/resources/files/type/file.object.json", SupportedFileType.JSON);
    }

    @Test
    public void testFileTypeJsonArray() {
        testPath("src/test/resources/files/type/file.array.json", SupportedFileType.JSON);
    }

    @Test
    public void testFileTypeMultipleJsonObjects() {
        testPath("src/test/resources/files/type/file.jsons.json", SupportedFileType.JSON);
    }

    @Test
    public void testFileEmptyJson() {
        testPath("src/test/resources/files/type/file.empty.json", SupportedFileType.JSON);
    }

    @Test
    public void testEmptyCsv() {
        testPath("src/test/resources/files/type/file.empty.csv", SupportedFileType.CSV);
    }

    @Test
    public void testCsv() {
        testPath("src/test/resources/files/type/file.full.csv", SupportedFileType.CSV);
    }

    @Test
    public void testGetFileSuccess() {
        final Stream<CompletableFuture<Path>> readedFiles = fileService.getFiles(new String[]{
                "src/test/resources/files/type/file.full.csv",
                "src/test/resources/files/type/file.empty.csv",
                "src/test/resources/files/type/file.empty.json",
                "src/test/resources/files/type/file.jsons.json"
        });
        readedFiles.map(pathCompletableFuture -> {
            try {
                return pathCompletableFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).forEach(path -> {
            assertTrue(Files.exists(path));
        });
    }

    private void testPath(String s, SupportedFileType supportedFileType) {
        final Path jsonTestPath = Paths.get(s);
        final SupportedFileType fileType = fileService.getFileTypeOfTheFile(jsonTestPath);
        assertEquals(supportedFileType, fileType);
    }
}