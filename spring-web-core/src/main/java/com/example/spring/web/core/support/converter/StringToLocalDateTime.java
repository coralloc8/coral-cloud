package com.example.spring.web.core.support.converter;

import java.time.LocalDateTime;

import org.springframework.core.convert.converter.Converter;

import com.example.spring.common.DatePattern;
import com.example.spring.common.DateTimeUtil;

/**
 * 全局字符串转日期时间
 * 
 * @author huss
 */
public class StringToLocalDateTime implements Converter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(String source) {
        if (source.isEmpty()) {
            // It's an empty enum identifier: reset the enum value to null.
            return null;
        }
        LocalDateTime localDateTime =
            LocalDateTime.from(DateTimeUtil.parse(source, DatePattern.YYYY_MM_DD_HH_MM_SS_EN));
        return localDateTime;
    }
}
