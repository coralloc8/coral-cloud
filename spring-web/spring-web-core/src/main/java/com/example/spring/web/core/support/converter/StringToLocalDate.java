package com.example.spring.web.core.support.converter;

import java.time.LocalDate;

import org.springframework.core.convert.converter.Converter;

import com.example.spring.common.DatePattern;
import com.example.spring.common.DateTimeUtil;

/**
 * 全局字符串转日期
 * 
 * @author huss
 */
public class StringToLocalDate implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String source) {
        if (source.isEmpty()) {
            // It's an empty enum identifier: reset the enum value to null.
            return null;
        }
        LocalDate localDate = LocalDate.from(DateTimeUtil.parse(source, DatePattern.YYYY_MM_DD_EN));
        return localDate;
    }
}
