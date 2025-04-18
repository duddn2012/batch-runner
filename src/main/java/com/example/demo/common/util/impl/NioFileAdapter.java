package com.example.demo.common.util.impl;

import com.example.demo.common.util.FileWriteUtil;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class NioFileAdapter implements FileWriteUtil {

    @Override
    public void overwriteAll(Path targetPath, List<String> lines) throws IOException {
        writeToFile(targetPath, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
    }

    @Override
    public void overwriteLine(Path targetPath, String line) throws IOException {
        writeToFile(targetPath, List.of(line), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
    }

    @Override
    public void appendWriteAll(Path targetPath, List<String> lines) throws IOException {
        writeToFile(targetPath, lines, StandardOpenOption.CREATE, StandardOpenOption.APPEND, StandardOpenOption.WRITE);
    }

    @Override
    public void appendWriteLine(Path targetPath, String line) throws IOException {
        writeToFile(targetPath, List.of(line), StandardOpenOption.CREATE, StandardOpenOption.APPEND, StandardOpenOption.WRITE);
    }

    private static void writeToFile(Path targetPath, List<String> lines, StandardOpenOption... standardOpenOptions) throws IOException {
        Files.createDirectories(targetPath.getParent());

        try (BufferedWriter writer = Files.newBufferedWriter(targetPath, standardOpenOptions)) {
            for (String line: lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }
}
