package com.coral.database.test.mybatis.config.datasource.primary;

import com.coral.database.test.mybatis.config.datasource.AtomikosDataSourceProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author huss
 * @version 1.0
 * @className DataSourcePrimaryConfig
 * @description 数据源
 * @date 2021/7/13 19:18
 */
@Component
@ConfigurationProperties(prefix = "spring.datasource.primary")
public class DataSourcePrimaryProperty extends AtomikosDataSourceProperty {
}
