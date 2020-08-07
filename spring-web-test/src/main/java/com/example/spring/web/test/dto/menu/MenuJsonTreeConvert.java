package com.example.spring.web.test.dto.menu;

import java.util.Comparator;

import com.example.spring.common.convert.AbstractJsonTreeConvert;
import com.example.spring.common.convert.SimpleJsonTree;

/**
 * @description: menu 转tree
 * @author: huss
 * @time: 2020/7/14 18:16
 */
public class MenuJsonTreeConvert extends AbstractJsonTreeConvert<SimpleMenuDTO, SimpleJsonTree> {

    private MenuJsonTreeConvert() {}

    public static MenuJsonTreeConvert newInstance() {
        return new MenuJsonTreeConvert();
    }

    @Override
    protected boolean isSort() {
        return true;
    }

    @Override
    protected String getOwnChildrenKeyVal(SimpleMenuDTO simpleMenuDTO) {
        return simpleMenuDTO.getNo();
    }

    @Override
    protected String getGroupByColumnVal(SimpleMenuDTO simpleMenuDTO) {
        return simpleMenuDTO.getParentNo();
    }

    @Override
    protected Comparator<SimpleMenuDTO> sorted() {
        // sort 大到小排序
        return Comparator.comparing(SimpleMenuDTO::getSort).reversed();
    }

    @Override
    protected SimpleJsonTree buildTree(SimpleMenuDTO simpleMenuDTO) {

        return new SimpleJsonTree();
    }

    @Override
    public String setTreeLabel(SimpleMenuDTO simpleMenuDTO) {
        return simpleMenuDTO.getName();
    }

    @Override
    public Object setTreeValue(SimpleMenuDTO simpleMenuDTO) {
        return simpleMenuDTO.getNo();
    }
}
