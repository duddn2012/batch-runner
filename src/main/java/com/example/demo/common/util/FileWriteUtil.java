package com.example.demo.common.util;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface FileWriteUtil {

    /**
     * 주어진 Path에 텍스트를 완전히 덮어써서 기록
     * @param targetPath 쓰기 대상 경로
     * @param lines 기록할 데이터 리스트
     * @throws IOException
     */
    void overwriteAll(Path targetPath, List<String> lines) throws IOException;


    /**
     * 주어진 Path에 텍스트를 완전히 덮어써서 기록
     * @param targetPath 쓰기 대상 경로
     * @param line 기록할 데이터 한 줄
     * @throws IOException
     */
    void overwriteLine(Path targetPath, String line) throws IOException;

    /**
     * 주어진 Path에 텍스트를 append 모드로 기록
     * @param targetPath 쓰기 대상 경로
     * @param lines 기록할 데이터 리스트
     * @throws IOException
     */
    void appendWriteAll(Path targetPath, List<String> lines) throws IOException;

    /**
     * 주어진 Path에 텍스트를 append 모드로 기록
     * @param targetPath 쓰기 대상 경로
     * @param line 기록할 데이터 리스트
     * @throws IOException
     */
    void appendWriteLine(Path targetPath, String line) throws IOException;
}
