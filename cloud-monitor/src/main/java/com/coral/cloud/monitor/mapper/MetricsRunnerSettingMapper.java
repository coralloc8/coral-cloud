package com.coral.cloud.monitor.mapper;

import com.coral.base.common.mybatis.mapper.MybatisMapper;
import com.coral.cloud.monitor.model.MetricsRunnerSetting;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author huss
 * @version 1.0
 * @className MetricsRunnerSettingMapper
 * @description 指标运行配置
 * @date 2023/4/17 10:26
 */
@Mapper
public interface MetricsRunnerSettingMapper extends MybatisMapper<MetricsRunnerSetting> {
}
