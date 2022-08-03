package com.coral.base.common;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author huss
 */
@Slf4j
public class IOUtil {

    public static String getMd5(InputStream in) throws IOException {
        return DigestUtils.md5Hex(in);

    }

    public static byte[] readByteArray(InputStream in, boolean escapeHtml4) throws IOException {
        if (escapeHtml4) {
            String readStream = readStream(in);
            readStream = StringEscapeUtils.escapeHtml4(readStream);
            return readStream.getBytes(StandardCharsets.UTF_8);
        } else {
            return readByteArray(in);
        }
    }

    public static byte[] readByteArray(InputStream in) throws IOException {
        Objects.requireNonNull(in);
        try (BufferedInputStream bufInput = new BufferedInputStream(in)) {
            int buffSize = 1024;
            ByteArrayOutputStream out = new ByteArrayOutputStream(buffSize);
            byte[] temp = new byte[buffSize];
            int size;
            while ((size = bufInput.read(temp)) != -1) {
                out.write(temp, 0, size);
            }
            return out.toByteArray();
        }
    }

    public static String readStream(InputStream inputStream) throws IOException {
        Objects.requireNonNull(inputStream);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            return br.lines().collect(Collectors.joining("\n"));
        } catch (IOException ioe) {
            log.error("error:", ioe);
            return "";
        }

    }

    public static List<String> readStreamAsArray(InputStream inputStream) throws IOException {
        Objects.requireNonNull(inputStream);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            return br.lines().collect(Collectors.toList());
        } catch (IOException ioe) {
            log.error("error:", ioe);

        }
        return Collections.emptyList();

    }
}
