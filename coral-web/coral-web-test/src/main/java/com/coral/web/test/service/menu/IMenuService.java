package com.coral.web.test.service.menu;

import java.util.List;

import com.coral.base.common.jpa.service.IJpaBaseService;
import com.coral.database.test.jpa.primary.entity.SysMenu;
import com.coral.web.test.dto.menu.MenuDTO;
import com.coral.web.test.vo.menu.request.MenuFilter;

/**
 * @description: 菜单
 * @author: huss
 * @time: 2020/7/14 15:37
 */
public interface IMenuService extends IJpaBaseService<SysMenu, Long> {

    /**
     * 查询导航栏菜单
     * 
     * @param menuFilter
     * @return
     */
    List<MenuDTO> findNavigationBarMenus(MenuFilter menuFilter);

}
