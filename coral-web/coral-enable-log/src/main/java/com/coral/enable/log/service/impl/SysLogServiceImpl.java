package com.coral.enable.log.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.coral.enable.log.service.SysLogService;
import com.coral.database.test.entity.SysLog;
import com.coral.database.test.repository.SysLogRepository;
import org.springframework.stereotype.Service;

/**
 * @description: 日志
 * @author: huss
 * @time: 2020/10/21 14:49
 */
@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogRepository sysLogRepository;

    @Override
    public void save(SysLog sysLog) {
        sysLogRepository.saveAndFlush(sysLog);
    }
}
