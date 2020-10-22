package com.example.spring.common.jpa.enums;

import com.example.spring.common.enums.IEnum;

/**
 * 可用状态
 * 
 * @author huss
 */

public enum GlobalEnabledEnum implements IEnum<GlobalEnabledEnum, Integer> {

    // 1:enable 0:disable

    /**
     * enable
     */
    ENABLE(1, "enable"),
    /**
     * disable
     */
    DISABLE(0, "disable"),

    ;

    private Integer code;

    private String name;

    GlobalEnabledEnum(Integer code, String name) {
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

    public static class Convert extends AbstractEnumConvert<GlobalEnabledEnum, Integer> {
        public Convert() {
            super(GlobalEnabledEnum.class);
        }
    }
}