package com.coral.database.test.repository;

import org.springframework.stereotype.Repository;

import com.coral.base.common.jpa.repository.JpaBaseRepository;
import com.coral.database.test.entity.SysMenuAuthority;

/**
 * @description: 菜单权限
 * @author: huss
 * @time: 2020/7/13 11:33
 */
@Repository
public interface SysMenuAuthorityRepository extends JpaBaseRepository<SysMenuAuthority, Long> {}
