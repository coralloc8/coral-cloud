package com.coral.cloud.monitor.mapper;

import com.coral.base.common.mybatis.mapper.MybatisMapper;
import com.coral.cloud.monitor.model.MetricsSetting;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author huss
 * @version 1.0
 * @className MetricsSettingMapper
 * @description 指标配置
 * @date 2023/4/17 10:26
 */
@Mapper
public interface MetricsSettingMapper extends MybatisMapper<MetricsSetting> {
}
