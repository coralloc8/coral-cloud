package com.coral.database.test.jpa.repository;

import org.springframework.stereotype.Repository;

import com.coral.base.common.jpa.repository.JpaBaseRepository;
import com.coral.database.test.jpa.entity.SysLog;

/**
 * @description: 日志
 * @author: huss
 * @time: 2020/10/21 14:46
 */
@Repository
public interface SysLogRepository extends JpaBaseRepository<SysLog, Long> {}
