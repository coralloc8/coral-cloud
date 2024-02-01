package com.coral.cloud.monitor.repository.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.coral.base.common.convert.PojoConvertUtil;
import com.coral.base.common.mybatis.service.IMybatisService;
import com.coral.base.common.mybatis.service.impl.MybatisServiceImpl;
import com.coral.cloud.monitor.dto.MetricsSettingInfoDTO;
import com.coral.cloud.monitor.mapper.MetricsSettingMapper;
import com.coral.cloud.monitor.model.MetricsSetting;
import com.coral.cloud.monitor.repository.MetricsSettingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author huss
 * @version 1.0
 * @className MetricsSettingRepositoryImpl
 * @description 指标设置
 * @date 2023/4/17 11:16
 */
@Slf4j
@Repository
public class MetricsSettingRepositoryImpl extends MybatisServiceImpl<MetricsSettingMapper, MetricsSetting>
        implements MetricsSettingRepository, IMybatisService<MetricsSetting> {

    @Override
    public Optional<MetricsSettingInfoDTO> findMetricsSetting(String metricsNo) {
        Wrapper<MetricsSetting> wrapper = new QueryWrapper<MetricsSetting>().lambda()
                .eq(MetricsSetting::getMetricsNo, metricsNo);
        MetricsSetting setting = this.getOne(wrapper);
        return Optional.ofNullable(PojoConvertUtil.convert(setting, MetricsSettingInfoDTO.class));
    }

    @Override
    public List<MetricsSettingInfoDTO> findMetricsSettings(Collection<String> metricsNos) {
        Wrapper<MetricsSetting> wrapper = new QueryWrapper<MetricsSetting>().lambda()
                .in(MetricsSetting::getMetricsNo, metricsNos);
        List<MetricsSetting> settings = this.list(wrapper);
        return PojoConvertUtil.batchConvert(settings, MetricsSettingInfoDTO.class);
    }
}
