package com.example.spring.database.test.repository;

import org.springframework.stereotype.Repository;

import com.example.spring.common.jpa.repository.JpaBaseRepository;
import com.example.spring.database.test.entity.SysRoleRight;

/**
 * @description: 角色权限
 * @author: huss
 * @time: 2020/7/13 11:35
 */
@Repository
public interface SysRoleRightRepository extends JpaBaseRepository<SysRoleRight, Long> {}
