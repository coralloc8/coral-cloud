package com.coral.test.opendoc.common.enums;

import com.coral.base.common.enums.IEnum;
import lombok.Getter;

/**
 * @className GlobalOrder
 * @description 通用排序
 * @author huss
 * @date 2021/9/14 16:12
 * @version 1.0
 */
public enum GlobalOrder implements IEnum<GlobalOrder,String> {
    /**
     * asc
     */
    ASC("asc", "升序"),

    /**
     * desc
     */
    DESC("desc", "倒序"),

    ;


    GlobalOrder(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Getter
    private String code;

    @Getter
    private String name;
}
