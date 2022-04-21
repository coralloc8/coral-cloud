package com.coral.cloud.user.common.constants;

/**
 * @author huss
 * @version 1.0
 * @className MqConstant
 * @description 消息队列常量值
 * @date 2022/4/20 10:04
 */
public interface MqConstant {
    /**
     * 全局uid
     */
    String COM_UID = "COMMON_UID";

    /**
     * 全局业务唯一编码
     */
    String COM_BUSINESS_NO = "COMMON_BUSINESS_NO";
    /**
     * 全局type
     */
    String COM_TYPE = "COMMON_TYPE";

    String ROCKET_KEYS = "KEYS";
    String ROCKET_TAGS = "TAGS";
    /**
     * rocketmq开源版只有这些等级(0-18)可配置：
     * 0代表不延迟消费
     * 1-18级对应的延迟时间分别为：1s、 5s、 10s、 30s、 1m、 2m、 3m、 4m、 5m、 6m、 7m、 8m、 9m、 10m、 20m、 30m、 1h、 2h
     */
    String ROCKET_DELAY_TIME_LEVEL = "DELAY";
    String ROCKET_RETRY_TOPIC = "RETRY_TOPIC";
    String ROCKET_REAL_TOPIC = "REAL_TOPIC";
    String ROCKET_UNIQ_KEY = "UNIQ_KEY";

    /**
     * rabbitmq 延迟交换器插件
     */
    String RABBIT_DELAY = "x-delay";


}
