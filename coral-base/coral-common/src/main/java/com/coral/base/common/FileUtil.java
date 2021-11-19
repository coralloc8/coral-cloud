package com.coral.base.common;

import com.coral.base.common.exception.SystemRuntimeException;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author huss
 */
@Slf4j
public class FileUtil {

    /**
     * 递归遍历文件下的所有文件
     *
     * @param dir
     * @param filter
     * @param files
     */
    public static void findAllFiles(@NonNull File dir, Predicate<File> filter, List<File> files) {
        File[] currentFiles = dir.listFiles();
        for (File file : currentFiles) {
            if (file.isFile() && filter.test(file)) {
                files.add(file);
            } else if (file.isDirectory()) {
                findAllFiles(file, filter, files);
            }
        }
    }


    public static Optional<String> findProjectRootPath() {
        try {
            String root = new File("").getCanonicalPath();
            return Optional.of(root);
        } catch (IOException e) {
            log.error("Error:", e);
        }
        return Optional.empty();
    }

    public static Optional<OutputStream> createFileOutputStream(String rootPath, String path) {
        try {
            Path filePath = Paths.get(rootPath, path);
            if (!Files.exists(filePath.getParent())) {
                Files.createDirectory(filePath.getParent());
            }

            return Optional.of(Files.newOutputStream(Paths.get(rootPath, path), StandardOpenOption.CREATE_NEW));
        } catch (IOException e) {
            log.error("Error:", e);
        }
        return Optional.empty();
    }

    /**
     * 读取文件
     *
     * @param inputStream 文件流
     * @param charset     字符编码
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
