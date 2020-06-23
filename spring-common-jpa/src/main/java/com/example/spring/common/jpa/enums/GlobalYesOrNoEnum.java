package com.example.spring.common.jpa.enums;

import com.example.spring.common.enums.IEnum;

/**
 * yes no
 * 
 * @author huss
 */

public enum GlobalYesOrNoEnum implements IEnum<GlobalYesOrNoEnum, Integer> {

    // 1:yes 0:no

    /**
     * yes
     */
    YES(1, "yes"),
    /**
     * no
     */
    NO(0, "no"),

    ;

    private Integer code;

    private String name;

    GlobalYesOrNoEnum(Integer code, String name) {
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

    public static class Convert extends AbstractEnumConvert<GlobalYesOrNoEnum, Integer> {
        public Convert() {
            super(GlobalYesOrNoEnum.class);
        }
    }
}