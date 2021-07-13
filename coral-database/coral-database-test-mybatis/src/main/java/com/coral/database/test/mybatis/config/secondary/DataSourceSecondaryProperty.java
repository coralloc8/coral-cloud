package com.coral.database.test.mybatis.config.secondary;

import com.coral.database.test.mybatis.config.AtomikosDataSourceProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author huss
 * @version 1.0
 * @className DataSourceSecondary
 * @description todo
 * @date 2021/7/13 19:19
 */
@Component
@ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.secondary")
public class DataSourceSecondaryProperty extends AtomikosDataSourceProperty {
}
