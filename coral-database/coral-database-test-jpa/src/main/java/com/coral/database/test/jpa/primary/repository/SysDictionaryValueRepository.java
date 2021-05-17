package com.coral.database.test.jpa.primary.repository;

import org.springframework.stereotype.Repository;

import com.coral.base.common.jpa.repository.JpaBaseRepository;
import com.coral.database.test.jpa.primary.entity.SysDictionaryValue;

/**
 * @description: 字典值
 * @author: huss
 * @time: 2020/7/13 11:29
 */
@Repository
public interface SysDictionaryValueRepository extends JpaBaseRepository<SysDictionaryValue, Long> {}
