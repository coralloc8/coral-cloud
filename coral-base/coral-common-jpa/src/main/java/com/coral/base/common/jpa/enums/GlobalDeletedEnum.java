package com.coral.base.common.jpa.enums;

import com.coral.base.common.enums.IEnum;

/**
 * 删除状态
 *
 * @author huss
 */

public enum GlobalDeletedEnum implements IEnum<GlobalDeletedEnum, Integer> {

    // 1:yes 0:no

    /**
     * 正常
     */
    NORMAL(1, "正常"),
    /**
     * 已删除
     */
    DELETED(-1, "已删除"),

    ;

    private Integer code;

    private String name;

    GlobalDeletedEnum(Integer code, String name) {
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

    public static class Convert extends AbstractEnumConvert<GlobalDeletedEnum, Integer> {
        public Convert() {
            super(GlobalDeletedEnum.class);
        }
    }
}