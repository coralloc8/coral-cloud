package com.coral.test.spring.graphql_jdk8.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * 校验配置
 *
 * @author huss
 * @date 2024/1/4 19:43
 * @packageName com.coral.test.spring.graphql.config
 * @className ValidConfig
 */
@Configuration
public class ValidConfig {
    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                // 快速失败模式
                .failFast(true)
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }
}
