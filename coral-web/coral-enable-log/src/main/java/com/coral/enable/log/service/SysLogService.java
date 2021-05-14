package com.coral.enable.log.service;

import com.coral.database.test.jpa.entity.SysLog;

/**
 * @description: 日志
 * @author: huss
 * @time: 2020/10/21 14:48
 */
public interface SysLogService {

    void save(SysLog sysLog);
}
