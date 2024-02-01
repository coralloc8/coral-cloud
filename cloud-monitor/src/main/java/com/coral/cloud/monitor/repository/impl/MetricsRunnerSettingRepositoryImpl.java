package com.coral.cloud.monitor.repository.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.coral.base.common.convert.PojoConvertUtil;
import com.coral.base.common.mybatis.service.IMybatisService;
import com.coral.base.common.mybatis.service.impl.MybatisServiceImpl;
import com.coral.cloud.monitor.common.enums.NormalEnabled;
import com.coral.cloud.monitor.dto.MetricsRunnerSettingInfoDTO;
import com.coral.cloud.monitor.mapper.MetricsRunnerSettingMapper;
import com.coral.cloud.monitor.model.MetricsRunnerSetting;
import com.coral.cloud.monitor.repository.MetricsRunnerSettingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huss
 * @version 1.0
 * @className MetricsRunnerSettingRepositoryImpl
 * @description 指标运行设置
 * @date 2023/4/17 11:16
 */
@Slf4j
@Repository
public class MetricsRunnerSettingRepositoryImpl extends MybatisServiceImpl<MetricsRunnerSettingMapper, MetricsRunnerSetting>
        implements MetricsRunnerSettingRepository, IMybatisService<MetricsRunnerSetting> {

    @Override
    public List<MetricsRunnerSettingInfoDTO> findRunnerSettings(String hospitalCode) {
        Wrapper<MetricsRunnerSetting> wrapper = new QueryWrapper<MetricsRunnerSetting>().lambda()
                .eq(MetricsRunnerSetting::getHospitalCode, hospitalCode)
                .eq(MetricsRunnerSetting::getEnabled, NormalEnabled.ENABLE);
        List<MetricsRunnerSetting> settings = this.list(wrapper);
        return PojoConvertUtil.batchConvert(settings, MetricsRunnerSettingInfoDTO.class);
    }
}
