package com.example.spring.database.test.repository;

import org.springframework.stereotype.Repository;

import com.example.spring.common.jpa.repository.JpaBaseRepository;
import com.example.spring.database.test.entity.SysDictionaryValue;

/**
 * @description: 字典值
 * @author: huss
 * @time: 2020/7/13 11:29
 */
@Repository
public interface SysDictionaryValueRepository extends JpaBaseRepository<SysDictionaryValue, Long> {}
