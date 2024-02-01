package com.coral.test.spring.graphql.eums;

import lombok.Getter;

/**
 * 性别
 *
 * @author huss
 * @date 2023/12/28 13:56
 * @packageName com.coral.test.spring.graphql.eums
 * @className Sex
 */
public enum Sex implements BaseEnum<String> {

    /**
     * 男性
     */
    MALE("male", "男性"),

    /**
     * 女性
     */
    FEMALE("female", "女性");


    @Getter
    private final String code;

    @Getter
    private final String name;

    Sex(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
