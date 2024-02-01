package com.coral.cloud.monitor.repository.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.coral.base.common.convert.PojoConvertUtil;
import com.coral.base.common.mybatis.service.IMybatisService;
import com.coral.base.common.mybatis.service.impl.MybatisServiceImpl;
import com.coral.cloud.monitor.common.enums.NormalEnabled;
import com.coral.cloud.monitor.common.enums.NormalStatus;
import com.coral.cloud.monitor.dto.MetricsDataSourceInfoDTO;
import com.coral.cloud.monitor.mapper.MetricsDataSourceMapper;
import com.coral.cloud.monitor.model.MetricsDataSource;
import com.coral.cloud.monitor.repository.MetricsDataSourceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author huss
 * @version 1.0
 * @className MetricsDataSourceRepositoryImpl
 * @description 指标数据源
 * @date 2023/4/17 11:16
 */
@Slf4j
@Repository
public class MetricsDataSourceRepositoryImpl extends MybatisServiceImpl<MetricsDataSourceMapper, MetricsDataSource>
        implements MetricsDataSourceRepository, IMybatisService<MetricsDataSource> {

    @Override
    public Optional<MetricsDataSourceInfoDTO> findDataSource(String hospitalCode, String dsNo) {
        Wrapper<MetricsDataSource> wrapper = new QueryWrapper<MetricsDataSource>().lambda()
                .eq(MetricsDataSource::getHospitalCode, hospitalCode)
                .eq(MetricsDataSource::getDsNo, dsNo)
                .eq(MetricsDataSource::getStatus, NormalStatus.NORMAL)
                .eq(MetricsDataSource::getEnabled, NormalEnabled.ENABLE);
        MetricsDataSource dataSource = this.getOne(wrapper);
        return Optional.ofNullable(PojoConvertUtil.convert(dataSource, MetricsDataSourceInfoDTO.class));
    }

    @Override
    public List<MetricsDataSourceInfoDTO> findDataSources(String hospitalCode, Collection<String> dsNos) {
        Wrapper<MetricsDataSource> wrapper = new QueryWrapper<MetricsDataSource>().lambda()
                .eq(MetricsDataSource::getHospitalCode, hospitalCode)
                .in(MetricsDataSource::getDsNo, dsNos)
                .eq(MetricsDataSource::getStatus, NormalStatus.NORMAL)
                .eq(MetricsDataSource::getEnabled, NormalEnabled.ENABLE);
        List<MetricsDataSource> dataSources = this.list(wrapper);
        return PojoConvertUtil.batchConvert(dataSources, MetricsDataSourceInfoDTO.class);
    }
}
