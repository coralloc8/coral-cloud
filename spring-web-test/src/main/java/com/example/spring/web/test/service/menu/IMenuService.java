package com.example.spring.web.test.service.menu;

import java.util.List;

import com.example.spring.common.jpa.service.IJpaBaseService;
import com.example.spring.database.test.entity.SysMenu;
import com.example.spring.web.test.dto.menu.SimpleMenuDTO;
import com.example.spring.web.test.vo.menu.request.MenuFilter;

/**
 * @description: 菜单
 * @author: huss
 * @time: 2020/7/14 15:37
 */
public interface IMenuService extends IJpaBaseService<SysMenu, Long> {

    /**
     * 查询所有没有删除的菜单
     * 
     * @param menuFilter
     * @return
     */
    List<SimpleMenuDTO> findAllMenus(MenuFilter menuFilter);

}
