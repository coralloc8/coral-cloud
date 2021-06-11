package com.coral.base.common;

import com.coral.base.common.exception.SystemRuntimeException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huss
 */
@Slf4j
public class FileUtil {

    /**
     * 读取文件
     * @param inputStream 文件流
     * @param charset 字符编码
     * @return
     */
    public static List<String> readFile(FileInputStream inputStream, String charset) {
        List<String> lines = new ArrayList<>();
        try (FileChannel fileChannel = inputStream.getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
            while (fileChannel.read(buffer) != -1) {
                buffer.flip();
                lines.add(Charset.forName(charset).decode(buffer).toString());
                buffer.compact();
            }
        } catch (IOException e) {
            log.error(">>>>>Error:", e);
        }
        return lines;
    }

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
