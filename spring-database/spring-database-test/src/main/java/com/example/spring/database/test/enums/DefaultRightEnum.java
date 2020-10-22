package com.example.spring.database.test.enums;

import com.example.spring.common.enums.IEnum;
import com.example.spring.common.jpa.enums.AbstractEnumConvert;

/**
 * @description: 默认权限
 * @author: huss
 * @time: 2020/7/13 14:52
 */
public enum DefaultRightEnum implements IEnum<DefaultRightEnum, Integer> {
    /**
     * find：查询
     */
    FIND(1, "find", "查询"),

    /**
     * add：新增
     */
    ADD(2, "add", "新增"),

    /**
     * edit：编辑
     */
    EDIT(3, "edit", "编辑"),

    /**
     * delete：删除
     */
    DELETE(4, "delete", "删除"),

    /**
     * export：导出
     */
    EXPORT(5, "export", "导出"),

    /**
     * import：导入
     */
    IMPORT(6, "import", "导入"),

    ;

    private Integer code;

    private String name;

    private String description;

    DefaultRightEnum(Integer code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    public static class Convert extends AbstractEnumConvert<DefaultRightEnum, Integer> {
        public Convert() {
            super(DefaultRightEnum.class);
        }
    }

}
