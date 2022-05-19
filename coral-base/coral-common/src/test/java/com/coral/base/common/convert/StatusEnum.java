package com.coral.base.common.convert;

import com.coral.base.common.enums.IEnum;
import lombok.Getter;

/**
 * @author huss
 * @version 1.0
 * @className StatusEnum
 * @description 状态
 * @date 2022/5/18 16:39
 */
public enum StatusEnum implements IEnum<StatusEnum, String> {

    /**
     * 正常
     */
    NORMAL("normal", "正常"),

    /**
     * 已删除
     */
    DELETED("deleted", "已删除"),
    ;


    @Getter
    private String code;

    @Getter
    private String name;

    StatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
