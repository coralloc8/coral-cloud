package com.example.spring.common.excel.model;

import java.io.InputStream;
import java.util.List;

import com.example.spring.common.excel.AbstractExcelReadListener;

import lombok.*;

/**
 * @description: excel读取
 * @author: huss
 * @time: 2020/10/29 15:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExcelRead {

    /**
     * 数据流
     */
    private InputStream inputStream;

    /**
     * sheet
     */
    private List<SheetRead> sheetReads;

    @Data
    @NoArgsConstructor
    @Builder
    public static class SheetRead<VO extends IModel> {

        /**
         * 第几个sheet 从0开始
         */
        private int sheet;
        /**
         * sheet对应的数据类型class
         */
        private Class<VO> headVoClass;

        /**
         * 对应的监听器
         */
        private AbstractExcelReadListener<VO> listener;

        public SheetRead(int sheet, Class<VO> headVoClass, AbstractExcelReadListener<VO> listener) {
            this.sheet = sheet;
            this.headVoClass = headVoClass;
            this.listener = listener;
        }
    }
}
