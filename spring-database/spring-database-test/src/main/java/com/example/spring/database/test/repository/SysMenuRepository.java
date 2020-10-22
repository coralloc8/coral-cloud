package com.example.spring.database.test.repository;

import com.example.spring.common.jpa.repository.JpaBaseRepository;
import com.example.spring.database.test.entity.SysMenu;
import com.example.spring.database.test.repository.dsl.SysMenuDslRepository;

/**
 * @description: 菜单
 * @author: huss
 * @time: 2020/7/13 11:32
 */

public interface SysMenuRepository extends JpaBaseRepository<SysMenu, Long>, SysMenuDslRepository {

}
