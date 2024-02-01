package com.coral.test;

import com.coral.base.common.DateTimeUtil;
import com.coral.base.common.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class Test2 {

    public static void main(String[] args) throws IOException {
        String file = "C:\\Users\\huss\\Desktop\\1111.txt";
        String file2 = "C:\\Users\\huss\\Desktop\\2222.txt";

        Files.deleteIfExists(Paths.get(file2));
//
//        Files.readAllLines(Paths.get(file))
//                .forEach(line -> {
//                    String newLine = "";
//                    if (StringUtils.isNotBlank(line)) {
//                        TemporalAccessor temp = DateTimeUtil.parse(line, DateTimeFormatter.ofPattern("yyyy/M/d H:mm:ss"));
//                        newLine = DateTimeUtil.formatDateTime(temp);
//                    }
//                    newLine += "\n";
//                    try {
//                        Files.write(Paths.get(file2), newLine.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//
//                });

        StringBuilder sb = new StringBuilder();
        Files.readAllLines(Paths.get(file))
                .forEach(line -> {
                    String newLine = "";
                    if (StringUtils.isNotBlank(line)) {
                        TemporalAccessor temp = DateTimeUtil.parse(line, DateTimeFormatter.ofPattern("yyyy/M/d H:mm:ss"));
                        newLine = DateTimeUtil.formatDateTime(temp);
                    }
                    newLine += "\n";
                    sb.append(newLine);
                });

        try {
            Files.write(Paths.get(file2), sb.toString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        TemporalAccessor temp =  DateTimeUtil.parse(date, DateTimeFormatter.ofPattern("yyyy/M/d H:mm:ss"));
//        System.out.println(LocalDateTime.from(temp));

    }
}
