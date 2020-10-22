package com.example.spring.database.test.repository;

import org.springframework.stereotype.Repository;

import com.example.spring.common.jpa.repository.JpaBaseRepository;
import com.example.spring.database.test.entity.SysLog;

/**
 * @description: 日志
 * @author: huss
 * @time: 2020/10/21 14:46
 */
@Repository
public interface SysLogRepository extends JpaBaseRepository<SysLog, Long> {}
