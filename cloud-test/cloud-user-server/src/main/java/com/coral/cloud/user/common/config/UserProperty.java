package com.coral.cloud.user.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author huss
 * @version 1.0
 * @className UserProperty
 * @description 用户配置
 * @date 2022/4/6 12:40
 */
@RefreshScope
@ConfigurationProperties(prefix = "user")
@Component
@Data
public class UserProperty {
    private String username;
}
