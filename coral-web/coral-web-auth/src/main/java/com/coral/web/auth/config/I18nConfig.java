package com.coral.web.auth.config;

import java.util.LinkedList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.coral.web.core.interceptor.LocaleChangeInterceptor;

/**
 * @author huss
 */
@Configuration
public class I18nConfig {

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        List<LocaleChangeInterceptor.Param> params = new LinkedList<>();
        params.add(new LocaleChangeInterceptor.Param("lang", LocaleChangeInterceptor.ParamFromEnum.PATH));
        params.add(new LocaleChangeInterceptor.Param("Accept-Language", LocaleChangeInterceptor.ParamFromEnum.HEADER));
        localeChangeInterceptor.setParams(params);
        return localeChangeInterceptor;
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new SessionLocaleResolver();
    }
}
