package com.coral.database.test.jpa.repository;

import com.coral.database.test.jpa.entity.SysRole;
import org.springframework.stereotype.Repository;

import com.coral.base.common.jpa.repository.JpaBaseRepository;

/**
 * @description: 角色
 * @author: huss
 * @time: 2020/7/13 11:33
 */
@Repository
public interface SysRoleRepository extends JpaBaseRepository<SysRole, Long> {}
