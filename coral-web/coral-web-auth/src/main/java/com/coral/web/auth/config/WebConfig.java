package com.coral.web.auth.config;

import java.util.List;

import com.coral.web.core.json.Jackson2HttpMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.coral.service.file.config.UploadProperty;
import com.coral.web.core.interceptor.LocaleChangeInterceptor;

/**
 * @author huss
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LocaleChangeInterceptor localeChangeInterceptor;
    @Autowired
    private UploadProperty uploadProperty;

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

    /**
     * 拦截器
     * 
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 国际化
        registry.addInterceptor(localeChangeInterceptor).addPathPatterns("/**");
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.removeIf(converter -> converter instanceof MappingJackson2HttpMessageConverter);
        Jackson2HttpMessageConverter jackson2HttpMessageConverter = new Jackson2HttpMessageConverter();
        converters.add(jackson2HttpMessageConverter);
    }

    /**
     * 配置静态资源
     * 
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler(uploadProperty.getVirtualPath() + "**")
            .addResourceLocations("file:" + uploadProperty.getSavePath());
    }

    /**
     * 防XSS注入
     *
     * @return FilterRegistrationBean
     */
    // @Bean
    // public FilterRegistrationBean<XssFilter> xssFilterRegistration() {
    // FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<>(new XssFilter());
    // registration.addUrlPatterns("/*");
    // registration.setName("xssFilter");
    // return registration;
    // }

}
