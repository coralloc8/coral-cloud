package com.coral.cloud.monitor.common.enums;

import com.coral.base.common.enums.IEnum;
import com.coral.base.common.mybatis.enums.IEnumMapping;
import lombok.Getter;

/**
 * @author huss
 * @version 1.0
 * @className NormalStatus
 * @description 状态
 * @date 2023/4/17 9:03
 */
public enum NormalStatus implements IEnum<NormalStatus, Integer>, IEnumMapping<Integer> {

    /**
     * 正常
     */
    NORMAL(1, "正常"),

    /**
     * 已删除
     */
    DELETED(0, "已删除"),


    ;

    NormalStatus(Integer code, String name) {
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