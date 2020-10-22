package com.example.spring.database.test.repository;

import org.springframework.stereotype.Repository;

import com.example.spring.common.jpa.repository.JpaBaseRepository;
import com.example.spring.database.test.entity.SysI18n;

/**
 * @description: 国际化
 * @author: huss
 * @time: 2020/7/13 11:31
 */
@Repository
public interface SysI18nRepository extends JpaBaseRepository<SysI18n, Long> {}
