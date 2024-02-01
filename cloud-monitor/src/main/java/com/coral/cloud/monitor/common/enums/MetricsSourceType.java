package com.coral.cloud.monitor.common.enums;

import com.coral.base.common.enums.IEnum;
import com.coral.base.common.mybatis.enums.IEnumMapping;
import lombok.Getter;

/**
 * @author huss
 * @version 1.0
 * @className MetricsSourceType
 * @description 指标源类型
 * @date 2023/4/12 11:02
 */
public enum MetricsSourceType implements IEnum<MetricsSourceType, String>, IEnumMapping<String> {

    /**
     * api
     */
    API("api", "api"),

    /**
     * jdbc
     */
    JDBC("jdbc", "jdbc"),


    ;

    MetricsSourceType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Getter
    private String code;

    @Getter
    private String name;

    @Override
    public String getValue() {
        return code;
    }
}
