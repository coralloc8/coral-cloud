package com.coral.web.test.web;

import java.util.List;

import com.coral.web.test.service.menu.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coral.web.core.response.Result;
import com.coral.web.core.web.BaseController;
import com.coral.web.test.dto.menu.MenuDTO;
import com.coral.web.test.dto.menu.MenuNavigationBarJsonTreeConvert;
import com.coral.web.test.vo.menu.request.MenuFilter;
import com.coral.web.test.vo.menu.response.MenuJsonTreeVO;

/**
 * @description: 菜单
 * @author: huss
 * @time: 2020/7/14 18:24
 */

@RestController
@RequestMapping("/menus")
public class MenuController extends BaseController {

    @Autowired
    private IMenuService menuService;

    /**
     * 获取资源权限列表
     * 
     * @return
     */
    @GetMapping
    public Result list(MenuFilter menuFilter) {
        List<MenuDTO> menus = menuService.findNavigationBarMenus(menuFilter);
        List<MenuJsonTreeVO> menuTrees = MenuNavigationBarJsonTreeConvert.newInstance().convert(menus);
        return this.result(true, menuTrees);
    }

}
