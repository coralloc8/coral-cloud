package com.coral.cloud.user.common.enums;

import com.coral.base.common.enums.IEnum;
import lombok.Getter;

/**
 * @author huss
 * @version 1.0
 * @className Sex
 * @description 性别
 * @date 2022/4/28 10:24
 */
public enum Sex implements IEnum<Sex, String> {

    /**
     * 男
     */
    MALE("male", "男"),
    /**
     * 女
     */
    FEMALE("female", "女"),

    ;


    Sex(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Getter
    private String code;

    @Getter
    private String name;
}
