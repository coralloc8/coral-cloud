package com.coral.cloud.monitor.mapper;

import com.coral.base.common.mybatis.mapper.MybatisMapper;
import com.coral.cloud.monitor.model.MetricsDataSource;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author huss
 * @version 1.0
 * @className MetricsDataSourceMapper
 * @description 指标数据源
 * @date 2023/4/17 10:26
 */
@Mapper
public interface MetricsDataSourceMapper extends MybatisMapper<MetricsDataSource> {
}
