package com.example.spring.common;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.spring.common.exception.SystemRuntimeException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * @author huss
 */
@Slf4j
public class FileUtil {

    public static FilePath writeFile(FileInputStream inputStream, String fileName, Path rootPath) {
        Path relativePath = Paths.get("", DateTimeUtil.formatDate(LocalDate.now()).split("-"));
        Path parentPath = Paths.get(rootPath.toString(), relativePath.toString());
        try {
            if (!Files.exists(parentPath)) {
                Files.createDirectories(parentPath);
            }

            String[] files = fileName.split("\\.");
            String file = files[0];
            String suffix = files[1];

            String fullFile =
                file + "_" + DateTimeUtil.format(LocalDateTime.now(), DatePattern.YYYYMMDDHHMMSS_EN) + "." + suffix;

            Path fullPath = Paths.get(parentPath.toString(), fullFile);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            // 先标记
            bufferedInputStream.mark(bufferedInputStream.available() + 1);
            Files.copy(bufferedInputStream, fullPath);

            relativePath = Paths.get(relativePath.toString(), fullFile);

            // reset 从头读取
            bufferedInputStream.reset();
            String md5 = IOUtil.getMd5(bufferedInputStream);

            return new FilePath(relativePath.toString(), fullPath.toString(), rootPath.toString(), md5);
        } catch (IOException e) {
            throw new SystemRuntimeException("save file error", e);
        }
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    public static class FilePath {

        private String relativePath;

        private String fullPath;

        private String rootPath;

        private String md5;

    }
}
