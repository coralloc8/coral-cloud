package com.coral.database.test.jpa.repository;

import org.springframework.stereotype.Repository;

import com.coral.base.common.jpa.repository.JpaBaseRepository;
import com.coral.database.test.jpa.entity.SysRoleRight;

/**
 * @description: 角色权限
 * @author: huss
 * @time: 2020/7/13 11:35
 */
@Repository
public interface SysRoleRightRepository extends JpaBaseRepository<SysRoleRight, Long> {}
