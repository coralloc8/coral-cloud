package com.example.spring.common.excel.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: excel 写入
 * @author: huss
 * @time: 2020/10/29 15:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelWrite {

    /**
     * 写入的excel文件完整路径
     */
    private String filePath;

    /**
     * sheet写入
     */
    private List<SheetWrite> sheetWrites;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SheetWrite<VO extends IModel> {

        /**
         * @formatter:off
         * 
         * 第几个sheet 从0开始
         * 多个sheetWrite对应的话必须填写
         * 
         * @formatter:on
         */
        private int sheet;

        /**
         * @formatter:off
         * 
         * sheet名称 每个sheet名称不能重复 
         * 可为空，空的话则为excel默认的sheet名称
         * 
         * @formatter:on
         */
        private String sheetName;

        /**
         * sheet对应的数据类型class
         */
        private Class<VO> headVoClass;

        /**
         * 要写入的数据
         */
        private List<VO> data;

    }

}
