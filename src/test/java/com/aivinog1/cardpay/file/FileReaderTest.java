package com.aivinog1.cardpay.file;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.aivinog1.cardpay.TestUtils.readToString;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileReaderTest {

    @Autowired
    private FileReader fileReader;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testFileExists() throws ExecutionException, InterruptedException, IOException {
        final CompletableFuture<Path> pathCompletableFuture = fileReader.readFile("src/test/resources/files/file.json");
        final Path path = pathCompletableFuture.get();
        assertNotNull(path);
        final String readedFileContent = new String(Files.readAllBytes(path));
        try (final InputStream expectedFileStream = FileReaderTest.class.getResourceAsStream("/files/file.json")){
            assertEquals(readToString(expectedFileStream), readedFileContent);
        }
    }

    @Test
    public void testFileNotExists() throws ExecutionException, InterruptedException {
        final CompletableFuture<Path> pathCompletableFuture = fileReader.readFile("NOT_EXISTS_FILE");
        expectedException.expect(ExecutionException.class);
        expectedException.expectMessage("File: NOT_EXISTS_FILE doesn't exists.");
        final Path path = pathCompletableFuture.get();
        assertNull(path);
    }
}