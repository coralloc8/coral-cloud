package com.example.spring.common.log.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring.common.log.service.SysLogService;
import com.example.spring.database.test.entity.SysLog;
import com.example.spring.database.test.repository.SysLogRepository;

/**
 * @description: 日志
 * @author: huss
 * @time: 2020/10/21 14:49
 */
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogRepository sysLogRepository;

    @Override
    public void save(SysLog sysLog) {
        sysLogRepository.saveAndFlush(sysLog);
    }
}
