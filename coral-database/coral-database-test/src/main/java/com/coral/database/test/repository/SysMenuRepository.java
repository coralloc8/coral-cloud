package com.coral.database.test.repository;

import com.coral.database.test.repository.dsl.SysMenuDslRepository;
import com.coral.base.common.jpa.repository.JpaBaseRepository;
import com.coral.database.test.entity.SysMenu;

/**
 * @description: 菜单
 * @author: huss
 * @time: 2020/7/13 11:32
 */

public interface SysMenuRepository extends JpaBaseRepository<SysMenu, Long>, SysMenuDslRepository {

}
