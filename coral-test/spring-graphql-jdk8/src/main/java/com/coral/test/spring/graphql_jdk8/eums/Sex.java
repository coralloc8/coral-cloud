package com.coral.test.spring.graphql_jdk8.eums;

import com.coral.base.common.enums.IEnum;
import com.coral.base.common.mybatis.enums.IEnumMapping;
import lombok.Getter;

/**
 * 性别
 *
 * @author huss
 * @date 2023/12/28 13:56
 * @packageName com.coral.test.spring.graphql.eums
 * @className Sex
 */
public enum Sex implements BaseEnum<String>, IEnum<Sex, String>, IEnumMapping<String> {

    /**
     * 男性
     */
    MALE("MALE", "男性"),

    /**
     * 女性
     */
    FEMALE("FEMALE", "女性");


    @Getter
    private final String code;

    @Getter
    private final String name;

    Sex(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String getValue() {
        return getCode();
    }
}
