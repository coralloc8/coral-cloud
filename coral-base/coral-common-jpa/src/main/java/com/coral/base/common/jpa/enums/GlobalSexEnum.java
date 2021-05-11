package com.coral.base.common.jpa.enums;

import com.coral.base.common.enums.IEnum;

/**
 * 性别
 * 
 * @author huss
 */

public enum GlobalSexEnum implements IEnum<GlobalSexEnum, Integer> {

    /**
     * male
     */
    MALE(1, "male"),
    /**
     * female
     */
    FEMALE(2, "female"),

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

    public static class Convert extends AbstractEnumConvert<GlobalSexEnum, Integer> {
        public Convert() {
            super(GlobalSexEnum.class);
        }
    }
}