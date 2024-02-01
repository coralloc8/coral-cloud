package com.coral.test.spring.graphql_jdk8.config.graphql;

import graphql.ErrorClassification;

/**
 * GraphQlError
 *
 * @author huss
 * @date 2024/1/5 9:31
 * @packageName com.coral.test.spring.graphql.config.graphql
 * @className GraphQlError
 */
public enum GraphQlError implements ErrorClassification {
    /**
     * 参数校验错误
     */
    PARAMETER_VALIDATION_ERROR,

    /**
     * 业务抛出错误
     */
    BUSINESS_ERROR,

    /**
     * 数据获取失败
     */
    DATA_FETCHING_ERROR,
    /**
     * 系统异常
     */
    SYSTEM_ERROR,
}

