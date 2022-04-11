package com.coral.cloud.order.common.config;


import com.coral.web.core.json.Jackson2HttpMessageConverter;
import com.coral.web.core.support.converter.StringToEnumConverterFactory;
import com.coral.web.core.support.converter.StringToLocalDate;
import com.coral.web.core.support.converter.StringToLocalDateTime;
import com.coral.web.core.support.converter.StringToLocalTime;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @author huss
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new StringToEnumConverterFactory());
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

    /**
     * 跨域
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600);
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("swagger-ui/index.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        //springboot 集成swagger2.2后静态资源404，添加如下两行配置
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }
}
