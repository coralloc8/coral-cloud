package com.example.spring.web.test.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.common.convert.SimpleJsonTree;
import com.example.spring.web.core.response.Result;
import com.example.spring.web.core.web.BaseController;
import com.example.spring.web.test.dto.menu.MenuJsonTreeConvert;
import com.example.spring.web.test.dto.menu.SimpleMenuDTO;
import com.example.spring.web.test.service.menu.IMenuService;
import com.example.spring.web.test.vo.menu.request.MenuFilter;

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

    @GetMapping("/tree")
    public Result tree() {
        List<SimpleMenuDTO> menus = menuService.findAllMenus(new MenuFilter());
        List<SimpleJsonTree> trees = MenuJsonTreeConvert.newInstance().convert(menus);
        return this.result(true, trees);
    }

}
