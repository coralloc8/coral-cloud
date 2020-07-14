package com.example.spring.database.test.repository;

import org.springframework.stereotype.Repository;

import com.example.spring.common.jpa.repository.JpaBaseRepository;
import com.example.spring.database.test.entity.SysMenuAuthority;

/**
 * @description: 菜单权限
 * @author: huss
 * @time: 2020/7/13 11:33
 */
@Repository
public interface SysMenuAuthorityRepository extends JpaBaseRepository<SysMenuAuthority, Long> {}
