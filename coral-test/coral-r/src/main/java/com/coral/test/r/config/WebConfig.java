package com.coral.test.r.config;

import com.coral.web.core.aop.LogAop;
import com.coral.web.core.json.Jackson2HttpMessageConverter;
import com.coral.web.core.support.converter.StringToEnumConverterFactory;
import com.coral.web.core.support.converter.StringToLocalDate;
import com.coral.web.core.support.converter.StringToLocalDateTime;
import com.coral.web.core.support.converter.StringToLocalTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @description: web config配置
 * @author: huss
 * @time: 2020/11/11 14:40
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    /**
     * 请求日志打印
     *
     * @return
     */
    @Bean
    public LogAop logAop() {
        return new LogAop();
    }


    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new StringToEnumConverterFactory());
        registry.addConverter(new StringToLocalDate());
        registry.addConverter(new StringToLocalDateTime());
        registry.addConverter(new StringToLocalTime());

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //这是配置模板页面的路径  配置文件里面已经配置了  所以这里暂时不需要
        //这是是配置静态资源文件的路径
        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
        super.addResourceHandlers(registry);
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
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.removeIf(converter -> converter instanceof MappingJackson2HttpMessageConverter);
        Jackson2HttpMessageConverter jackson2HttpMessageConverter = new Jackson2HttpMessageConverter();
        converters.add(jackson2HttpMessageConverter);
    }


}
