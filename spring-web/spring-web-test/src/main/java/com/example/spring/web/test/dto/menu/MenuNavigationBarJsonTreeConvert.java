package com.example.spring.web.test.dto.menu;

import java.util.Comparator;

import com.example.spring.common.convert.AbstractJsonTreeConvert;
import com.example.spring.web.test.vo.menu.response.MenuJsonTreeVO;

/**
 * @description: menu 转导航栏
 * @author: huss
 * @time: 2020/7/14 18:16
 */
public class MenuNavigationBarJsonTreeConvert extends AbstractJsonTreeConvert<MenuDTO, MenuJsonTreeVO> {

    private MenuNavigationBarJsonTreeConvert() {}

    public static MenuNavigationBarJsonTreeConvert newInstance() {
        return new MenuNavigationBarJsonTreeConvert();
    }

    @Override
    protected boolean isSort() {
        return true;
    }

    @Override
    protected String getOwnChildrenKeyVal(MenuDTO menuDTO) {
        return menuDTO.getNo();
    }

    @Override
    protected String getGroupByColumnVal(MenuDTO menuDTO) {
        return menuDTO.getParentNo();
    }

    @Override
    protected Comparator<MenuDTO> sorted() {
        // sort 大到小排序
        return Comparator.comparing(MenuDTO::getSort).reversed();
    }

    @Override
    protected MenuJsonTreeVO buildTree(MenuDTO menuDTO) {
        return new MenuJsonTreeVO().convert(menuDTO);
    }

    @Override
    public String setTreeLabel(MenuDTO menuDTO) {
        return menuDTO.getName();
    }

    @Override
    public Object setTreeValue(MenuDTO menuDTO) {
        return menuDTO.getNo();
    }
}
