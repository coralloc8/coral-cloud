package com.coral.test.opendoc.common.enums;

import com.coral.base.common.enums.IEnum;
import lombok.Getter;

/**
 * @author huss
 * @version 1.0
 * @className GlobalStatus
 * @description 通用状态枚举
 * @date 2021/9/14 16:02
 */
public enum GlobalStatus implements IEnum<GlobalStatus, Integer> {

    NORMAL(1, "正常"),
    DISABLED(0, "已禁用"),
    DELETED(-1, "已删除");


    @Getter
    private Integer code;
    @Getter
    private String name;

    GlobalStatus(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }
}
