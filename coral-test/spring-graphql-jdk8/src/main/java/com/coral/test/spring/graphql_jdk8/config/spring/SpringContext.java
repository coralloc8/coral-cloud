package com.coral.test.spring.graphql_jdk8.config.spring;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * spring 上下文
 *
 * @author huss
 * @date 2024/1/5 11:25
 * @packageName com.coral.test.spring.graphql.util
 * @className SpringContext
 */
@Component
@Slf4j
public class SpringContext implements ApplicationContextAware {


    @Getter
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        if (SpringContext.applicationContext == null) {
            SpringContext.applicationContext = applicationContext;
        }
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    public static <T> Map<String, T> getBeansOfType(@Nullable Class<T> clazz) {
        return getApplicationContext().getBeansOfType(clazz);
    }
}
