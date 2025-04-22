package com.example.demo.common.util.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class NioFileAdapterTest {

    private final NioFileAdapter nioFileAdapter = new NioFileAdapter();

    @TempDir
    Path tempDir;

    @Test
    void overwriteAll() throws IOException {
        // given
        Path file = tempDir.resolve("logs/app.log");
        List<String> lines = List.of("하나", "둘", "셋");

        // when
         nioFileAdapter.overwriteAll(file, lines);

        // then
        assertTrue(Files.exists(file), "파일이 존재해야 함");
        List<String> actual = Files.readAllLines(file);
        assertEquals(lines, actual, "모든 라인이 정상적으로 Write 되어야 함");
    }

    @Test
    void overwriteLine() throws IOException {
        // given
        Path file = tempDir.resolve("logs/app.log");
        Files.createDirectories(file.getParent());
        Files.write(file, List.of("old data1", "old data2"));
        String newLine = "즐거운 개발^_^";

        // when
        nioFileAdapter.overwriteLine(file, newLine);

        // then
        List<String> actual = Files.readAllLines(file);
        assertEquals(1, actual.size(), "한 줄만 남아야 함");
        assertEquals(newLine, actual.get(0), "새로운 값이 Write 되어야 함");
    }

    @Test
    void appendWriteAll() throws IOException {
        // given
        Path file = tempDir.resolve("logs/app.log");
        Files.createDirectories(file.getParent());
        Files.write(file, List.of("old data1", "old data2"));
        List<String> lines = List.of("하나", "둘", "셋");

        // when
        nioFileAdapter.appendWriteAll(file, lines);

        // then
        List<String> actual = Files.readAllLines(file);
        assertEquals(2 + lines.size(), actual.size(), "기존 + 추가된 라인 수가 일치해야 함");
        assertEquals("old data1", actual.get(0));
        assertEquals("old data2", actual.get(1));
        assertEquals(lines, actual.subList(2, actual.size()));
    }

    @Test
    void appendWriteLine() throws IOException {
        // given
        Path file = tempDir.resolve("logs/app.log");
        Files.createDirectories(file.getParent());
        Files.write(file, List.of("old data1", "old data2"));
        String newLine = "행복한 개발:)";

        // when
        nioFileAdapter.appendWriteLine(file, newLine);

        // then
        List<String> actual = Files.readAllLines(file);
        assertEquals(2 + 1,  actual.size(), "기존 + 추가된 라인 수가 일치해야 함");
        assertEquals("old data1", actual.get(0));
        assertEquals("old data2", actual.get(1));
        assertEquals(newLine, actual.get(2));
    }
}