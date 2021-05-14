package com.coral.web.test.dto.menu;

import com.coral.base.common.convert.IConvert;
import com.coral.database.test.jpa.entity.SysMenu;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * @description: 精简的menu属性
 * @author: huss
 * @time: 2020/7/14 15:42
 */
@Getter
@ToString
public class SimpleMenuDTO implements IConvert<SysMenu, SimpleMenuDTO> {

    protected String no;

    private String parentNo;

    private String uniqueKey;

    private String name;

    private Integer sort;

    /**
     * 数据转换
     * 
     * @param e
     *            菜单数据
     * @return SimpleMenuDTO
     */
    @Override
    public SimpleMenuDTO convert(@NonNull SysMenu e) {
        this.no = e.getNo();
        this.parentNo = e.getParentNo();
        this.uniqueKey = e.getUniqueKey();
        this.name = e.getTitle();
        this.sort = e.getSort();
        return this;
    }

}
