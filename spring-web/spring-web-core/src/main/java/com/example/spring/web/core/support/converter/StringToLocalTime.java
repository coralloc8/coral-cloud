package com.example.spring.web.core.support.converter;

import java.time.LocalTime;

import org.springframework.core.convert.converter.Converter;

import com.example.spring.common.DatePattern;
import com.example.spring.common.DateTimeUtil;

/**
 * 全局字符串转时间
 * 
 * @author huss
 */
public class StringToLocalTime implements Converter<String, LocalTime> {
    @Override
    public LocalTime convert(String source) {
        if (source.isEmpty()) {
            // It's an empty enum identifier: reset the enum value to null.
            return null;
        }
        LocalTime localTime = LocalTime.from(DateTimeUtil.parse(source, DatePattern.HH_MM_SS_EN));
        return localTime;
    }
}
