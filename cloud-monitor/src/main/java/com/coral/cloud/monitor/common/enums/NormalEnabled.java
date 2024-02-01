package com.coral.cloud.monitor.common.enums;

import com.coral.base.common.enums.IEnum;
import com.coral.base.common.mybatis.enums.IEnumMapping;
import lombok.Getter;

/**
 * @author huss
 * @version 1.0
 * @className NormalEnabled
 * @description 启用状态
 * @date 2023/4/17 9:06
 */
public enum NormalEnabled implements IEnum<NormalEnabled, Integer>, IEnumMapping<Integer> {

    /**
     * 启用
     */
    ENABLE(1, "启用"),

    /**
     * 禁用
     */
    DISABLE(0, "禁用"),


    ;

    NormalEnabled(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    @Getter
    private Integer code;

    @Getter
    private String name;

    @Override
    public Integer getValue() {
        return code;
    }
}