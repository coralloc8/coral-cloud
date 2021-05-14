package com.coral.database.test.jpa.repository;

import org.springframework.stereotype.Repository;

import com.coral.base.common.jpa.repository.JpaBaseRepository;
import com.coral.database.test.jpa.entity.SysDictionaryType;

/**
 * @description: 字典类型
 * @author: huss
 * @time: 2020/7/13 11:28
 */
@Repository
public interface SysDictionaryTypeRepository extends JpaBaseRepository<SysDictionaryType, Long> {}
