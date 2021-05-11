package com.coral.web.core.interceptor;

import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

/***
 * i18n拦截器 可以识别url后面的lang参数和http header中的Accept-Language
 * 
 * @author huss
 * @see org.springframework.web.servlet.i18n.LocaleChangeInterceptor
 */
@Slf4j
public class LocaleChangeInterceptor extends HandlerInterceptorAdapter {

    private static final Pattern LOCALE_PATTERN = Pattern.compile("^\\s*(\\w{2})(?:-(\\w{2}))?(?:;q=(\\d+\\.\\d+))?$");

    /**
     * Default name of the locale specification parameter: "locale".
     */
    public static final String DEFAULT_PARAM_NAME = "locale";

    private List<Param> params;

    private String[] httpMethods;

    public List<Param> getParams() {
        return params;
    }

    public void setParams(List<Param> params) {
        this.params = params;
    }

    /**
     * Configure the HTTP method(s) over which the locale can be changed.
     *
     * @param httpMethods
     *            the methods
     * @since 4.2
     */
    public void setHttpMethods(String... httpMethods) {
        this.httpMethods = httpMethods;
    }

    /**
     * Return the configured HTTP methods.
     *
     * @since 4.2
     */
    public String[] getHttpMethods() {
        return this.httpMethods;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws ServletException {
        log.info("#####LocaleChangeInterceptor start...");
        Locale locale = this.getLocale(request);
        if (locale != null && checkHttpMethod(request.getMethod())) {
            LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
            if (localeResolver == null) {
                throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
            }
            localeResolver.setLocale(request, response, locale);
        }
        // Proceed in any case.
        return true;
    }

    private Locale getLocale(HttpServletRequest request) {
        Locale locale = null;
        for (Param param : getParams()) {
            if (param.getFrom().equals(ParamFromEnum.PATH)) {
                locale = this.getLocaleFromPath(request, param);
            } else if (param.getFrom().equals(ParamFromEnum.HEADER)) {
                locale = this.getLocaleFromHeader(request, param);
            }
            if (locale != null) {
                log.info(">>>>>getLocale:{}", locale);
                return locale;
            }

        }
        return null;
    }

    private Locale getLocaleFromPath(HttpServletRequest request, Param param) {
        String localName = request.getParameter(param.getName());
        if (!StringUtils.isEmpty(localName)) {
            localName = localName.replace("-", "_");
            return StringUtils.parseLocaleString(localName);
        }
        return null;
    }

    // zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7,zh-TW;q=0.6
    private Locale getLocaleFromHeader(HttpServletRequest request, Param param) {
        String locale = request.getHeader(param.getName());
        if (StringUtils.isEmpty(locale)) {
            return null;
        }

        String[] locales = locale.split(",");

        return parseLocale(locales[0]);
    }

    private Locale parseLocale(String locale) {
        Matcher matcher = LOCALE_PATTERN.matcher(locale);
        if (!matcher.matches()) {
            log.error("Invalid locale:{}", locale);
            return null;
        }
        String language = matcher.group(1);
        String country = matcher.group(2);
        if (country == null) {
            country = "";
        }
        String qualifier = matcher.group(3);
        if (qualifier == null) {
            qualifier = "";
        }
        return new Locale(language, country, qualifier);
    }

    private boolean checkHttpMethod(String currentMethod) {
        if (ObjectUtils.isEmpty(getHttpMethods())) {
            return true;
        }
        for (String httpMethod : getHttpMethods()) {
            if (httpMethod.equalsIgnoreCase(currentMethod)) {
                return true;
            }
        }
        return false;
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Param {
        private String name;
        private ParamFromEnum from;
    }

    public enum ParamFromEnum {
        HEADER("header"), PATH("path");

        @Getter
        private String name;

        ParamFromEnum(String name) {
            this.name = name;
        }
    }

}
