package com.example.spring.database.test.enums;

import com.example.spring.common.enums.CacheEnum;

/**
 * @author huss
 */

public enum CacheKeyEnum implements CacheEnum<CacheKeyEnum> {
    /**
     * 1小时 login:username:{}
     */
    LOGIN_USERNAME(1 * 60 * 60, "login:username:{}"),

    ;

    private Integer time;

    private String name;

    CacheKeyEnum(Integer time, String name) {
        this.time = time;
        this.name = name;
    }

    @Override
    public Integer getCode() {
        return this.time + this.getRandomTime();
    }

    @Override
    public String getName() {
        return this.name;
    }
}
