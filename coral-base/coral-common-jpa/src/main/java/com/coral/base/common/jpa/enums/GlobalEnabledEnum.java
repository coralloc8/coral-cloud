package com.coral.base.common.jpa.enums;

import com.coral.base.common.enums.IEnum;

/**
 * 可用状态
 * 
 * @author huss
 */

public enum GlobalEnabledEnum implements IEnum<GlobalEnabledEnum, Integer> {

    // 1:enable 0:disable

    /**
     * 启用
     */
    ENABLE(1, "启用"),
    /**
     * 禁用
     */
    DISABLE(0, "禁用"),

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