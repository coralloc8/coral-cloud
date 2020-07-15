package com.example.spring.web.test.dto.menu;

import com.example.spring.database.test.entity.SysMenu;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * @description: 精简的menu属性
 * @author: huss
 * @time: 2020/7/14 15:42
 */
@Data
@Builder
public class SimpleMenuDTO {

    private String menuNo;

    private String parentNo;

    private String uniqueKey;

    private String menuName;

    private Integer sort;

    /**
     * 数据转换
     * 
     * @param e
     *            菜单数据
     * @return SimpleMenuDTO
     */
    public static SimpleMenuDTO convert(@NonNull SysMenu e) {
        return SimpleMenuDTO.builder()
            //
            .menuNo(e.getNo())
            //
            .menuName(e.getTitle())
            //
            .uniqueKey(e.getUniqueKey())
            //
            .parentNo(e.getParentNo())
            //
            .sort(e.getSort())
            //
            .build();
    }

}
