package com.coral.base.common.mybatis.dynamic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author huss
 * @version 1.0
 * @className DynamicDataSource
 * @description 动态数据源
 * @date 2021/5/20 15:39
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        String datasource = DbContextHolder.getCurrentDsStr();
        log.debug("使用数据源 {}", datasource);
        return datasource;
    }
}
