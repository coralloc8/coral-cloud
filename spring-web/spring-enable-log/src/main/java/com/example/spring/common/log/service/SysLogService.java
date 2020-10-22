package com.example.spring.common.log.service;

import com.example.spring.database.test.entity.SysLog;

/**
 * @description: 日志
 * @author: huss
 * @time: 2020/10/21 14:48
 */
public interface SysLogService {

    void save(SysLog sysLog);
}
