package com.coral.web.test.web;

import com.coral.web.test.service.menu.IMenuService;
import com.coral.web.core.response.Result;
import com.coral.web.core.web.BaseController;
import com.coral.web.test.dto.menu.MenuDTO;
import com.coral.web.test.dto.menu.MenuNavigationBarJsonTreeConvert;
import com.coral.web.test.vo.menu.request.MenuFilter;
import com.coral.web.test.vo.menu.response.MenuJsonTreeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description: 资源权限
 * @author: huss
 * @time: 2020/7/16 11:58
 */

@RestController
@RequestMapping("/authorities")
public class AuthorityController extends BaseController {

    @Autowired
    private IMenuService menuService;

    /**
     * 查询导航栏
     *
     * @return
     */
    @GetMapping("/navigation")
    public Result navigationBar() {
        List<MenuDTO> menus = menuService.findNavigationBarMenus(new MenuFilter());
        List<MenuJsonTreeVO> bars = MenuNavigationBarJsonTreeConvert.newInstance().convert(menus);
        return this.result(true, bars);
    }
}
