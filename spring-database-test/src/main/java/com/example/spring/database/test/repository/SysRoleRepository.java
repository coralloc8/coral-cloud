package com.example.spring.database.test.repository;

import com.example.spring.database.test.entity.SysRole;
import org.springframework.stereotype.Repository;

import com.example.spring.common.jpa.repository.JpaBaseRepository;

/**
 * @description: 角色
 * @author: huss
 * @time: 2020/7/13 11:33
 */
@Repository
public interface SysRoleRepository extends JpaBaseRepository<SysRole, Long> {}
