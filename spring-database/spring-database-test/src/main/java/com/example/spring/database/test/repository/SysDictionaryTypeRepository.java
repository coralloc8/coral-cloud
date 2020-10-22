package com.example.spring.database.test.repository;

import org.springframework.stereotype.Repository;

import com.example.spring.common.jpa.repository.JpaBaseRepository;
import com.example.spring.database.test.entity.SysDictionaryType;

/**
 * @description: 字典类型
 * @author: huss
 * @time: 2020/7/13 11:28
 */
@Repository
public interface SysDictionaryTypeRepository extends JpaBaseRepository<SysDictionaryType, Long> {}
