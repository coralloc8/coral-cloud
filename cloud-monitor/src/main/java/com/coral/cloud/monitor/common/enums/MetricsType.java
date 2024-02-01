package com.coral.cloud.monitor.common.enums;

import com.coral.base.common.enums.IEnum;
import com.coral.base.common.mybatis.enums.IEnumMapping;
import lombok.Getter;

/**
 * @author huss
 * @version 1.0
 * @className MetricsType
 * @description 指标类型
 * @date 2023/4/12 11:02
 */
public enum MetricsType implements IEnum<MetricsType, String>, IEnumMapping<String> {

    /**
     * 计数器
     */
    COUNTER("counter", "计数器"),

    /**
     * 仪表盘
     */
    GAUGE("gauge", "仪表盘"),

    /**
     * 计时器
     */
    TIMER("timer", "计时器"),

    /**
     * 长任务计时器
     */
    LONG_TASK_TIMER("long_task_timer", "长任务计时器"),

    /**
     * 直方图
     */
//    HISTOGRAM("histogram","直方图"),

    /**
     * 摘要
     */
    SUMMARY("summary", "摘要"),

    ;

    MetricsType(String code, String name) {
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
