package com.coral.test.groovy.util;

import com.coral.base.common.CollectionUtil;
import com.coral.base.common.IOUtil;
import com.coral.base.common.StringUtils;
import com.coral.base.common.cache.ICacheService;
import com.coral.base.common.cache.LocalCacheServiceImpl;
import com.coral.base.common.exception.Exceptions;
import com.coral.web.core.support.SpringContext;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.Md5Crypt;
import org.codehaus.groovy.runtime.InvokerHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author huss
 * @version 1.0
 * @className GroovyHelper
 * @description groovy
 * @date 2022/12/1 10:15
 */
@Slf4j
public class GroovyHelper {
    private final static String GROOVY = "[groovy]";
    private final static ICacheService<String, Object> LOCAL_CACHE = LocalCacheServiceImpl.getInstance();

    /**
     * 构建groovy并且执行脚本
     *
     * @param generalConfig
     * @param str
     * @param groovyProperties
     * @return
     */
    public static Object buildAndRun(DataSourceGeneralConfig generalConfig, String str, GroovyProperty... groovyProperties) {
        GroovyShell groovyShell = buildGroovyShell(generalConfig);
        return run(groovyShell, str, groovyProperties);
    }

    public static GroovyShell buildGroovyShell(DataSourceGeneralConfig generalConfig) {
        StringBuilder cacheKey = new StringBuilder("GroovyShell_").append(generalConfig.getDsUuid());

        if (CollectionUtil.isNotBlank(generalConfig.getExternalResources())) {
            String fileKey = generalConfig.getExternalResources().stream()
                    .filter(e -> StringUtils.isNotBlank(e.getValue()))
                    .sorted(Comparator.comparing(e -> e.getValue()))
                    .map(e -> {
                        try {
                            return IOUtil.getMd5(Files.newInputStream(Paths.get(e.getValue())));
                        } catch (IOException ex) {
                            throw Exceptions.unchecked(ex);
                        }

                    }).collect(Collectors.joining("_"));
            cacheKey.append(fileKey);
        }

        GroovyShell groovyShell = (GroovyShell) LOCAL_CACHE.get(cacheKey.toString(), () -> {
            log.info(">>>>>no cache,build GroovyShell.");
            GroovyShell localShell = new GroovyShell();
            if (CollectionUtil.isNotBlank(generalConfig.getExternalResources())) {
                generalConfig.getExternalResources().forEach(e -> localShell.getClassLoader().addClasspath(e.getValue()));
            }
            return localShell;
        }, GroovyShell.class);

        return groovyShell;
    }

    public static Object run(@NonNull GroovyShell groovyShell, String str, GroovyProperty... groovyProperties) {
        String cacheKey = "GroovyScript_" + Md5Crypt.md5Crypt(str.getBytes());

        Script script = (Script) LOCAL_CACHE.get(cacheKey,
                () -> {
                    log.info(">>>>>no cache,build Script.");
                    return groovyShell.parse(parseScript(str));
                }, Script.class);

        Binding binding = buildBinding();
        if (Objects.nonNull(groovyProperties)) {
            Stream.of(groovyProperties).forEach(e -> binding.setVariable(e.getKey(), e.getValue()));
        }

        //重新生成新的实例
        script = InvokerHelper.createScript(script.getClass(), binding);

        return script.run();
    }

    public static boolean isGroovyShell(String str) {
        return StringUtils.isNotBlank(str) && str.startsWith(GROOVY);
    }


    private static String parseScript(String str) {
        return isGroovyShell(str) ? str.substring(GROOVY.length()) : str;
    }


    private static Binding buildBinding() {
        Map variables = new HashMap();
        if (Objects.nonNull(SpringContext.getApplicationContext())) {
            variables = SpringContext.getApplicationContext().getBeansOfType(Object.class);
        }
        return new Binding(variables);
    }


    public static GroovyProperty buildGroovyProperty(String key, Object value) {
        return new GroovyProperty(key, value);
    }


    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Data
    public static class GroovyProperty {
        /**
         * key
         */
        private String key;

        /**
         * 值
         */
        private Object value;
    }

}
