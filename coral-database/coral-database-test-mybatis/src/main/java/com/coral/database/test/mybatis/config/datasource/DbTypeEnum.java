package com.coral.database.test.mybatis.config.datasource;

import com.coral.base.common.mybatis.dynamic.DbType;
import lombok.Getter;

/**
 * @author huss
 * @version 1.0
 * @className DbTypeImpl
 * @description
 * @date 2021/5/20 15:48
 */
public enum DbTypeEnum implements DbType<DbTypeEnum, String> {

    /**
     * 主数据源
     */
    PRIMARY("primary", "主数据源"),

    /**
     * 测试第二数据源
     */
    SECONDARY("secondary", "测试第二数据源"),
    /**
     * 测试第三数据源
     */
    TERTIARY("tertiary","测试第三数据源")
    ;


    @Getter
    private String code;
    @Getter
    private String name;

    DbTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
