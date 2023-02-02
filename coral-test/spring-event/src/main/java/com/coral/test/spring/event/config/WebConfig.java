package com.coral.test.spring.event.config;

import com.coral.web.core.json.Jackson2HttpMessageConverter;
import com.coral.web.core.json.MyJackson2ObjectMapperBuilder;
import com.coral.web.core.support.converter.StringToEnumGenericConverter;
import com.coral.web.core.support.converter.StringToLocalDate;
import com.coral.web.core.support.converter.StringToLocalDateTime;
import com.coral.web.core.support.converter.StringToLocalTime;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author huss
 * @version 1.0
 * @className WebConfig
 * @description todo
 * @date 2023/2/2 16:04
 */
@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
//        registry.addConverterFactory(new StringToEnumConverterFactory());
        registry.addConverter(new StringToEnumGenericConverter());
        registry.addConverter(new StringToLocalDate());
        registry.addConverter(new StringToLocalDateTime());
        registry.addConverter(new StringToLocalTime());
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.removeIf(converter -> converter instanceof MappingJackson2HttpMessageConverter);
        Jackson2HttpMessageConverter jackson2HttpMessageConverter = new Jackson2HttpMessageConverter();
        converters.add(jackson2HttpMessageConverter);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return MyJackson2ObjectMapperBuilder.json().build();
    }

    /**
     * 处理 yml文件中配置的字符串转枚举
     *
     * @return
     */
    @Bean
    @ConfigurationPropertiesBinding
    public GenericConverter StringToEnumGenericConverter() {
        return new StringToEnumGenericConverter();
    }


}
