package com.coral.base.common.mybatis.enums;

import com.coral.base.common.enums.IEnum;

/**
 * 性别
 *
 * @author huss
 */

public enum GlobalSexEnum implements IEnum<GlobalSexEnum, Integer>, IEnumMapping<Integer> {

    /**
     * 男
     */
    MALE(1, "男"),
    /**
     * 女
     */
    FEMALE(0, "女"),

    ;

    private Integer code;

    private String name;

    GlobalSexEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }


    @Override
    public Integer getValue() {
        return code;
    }
}