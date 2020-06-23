package com.example.spring.common;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * @author huss
 */
@Slf4j
public class IOUtil {

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
        StringBuilder stringBuilder = new StringBuilder();


        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

            return br.lines().collect(Collectors.joining("\n"));
        } catch (IOException ioe) {
            // return getKeyFromAuthorizationServer();
            log.error("error:",ioe);
            return "";
        }

    }
}
