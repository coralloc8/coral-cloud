package com.coral.base.common.excel.model;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.excel.enums.WriteDirectionEnum;

import lombok.*;

/**
 * @description: excel填充
 * @author: huss
 * @time: 2020/11/2 15:09
 */
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExcelFill {

    /**
     * 模板文件路径
     */
    private String templateFilePath;

    /**
     * 模板输入流
     */
    private InputStream templateInputStream;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件输出流
     */
    private OutputStream fileOutputStream;


    /**
     * 数据填充
     */
    private List<SheetFill> sheetFills;

    public ExcelFill(InputStream templateInputStream, String filePath, List<SheetFill> sheetFills) {
        this.templateInputStream = templateInputStream;
        this.filePath = filePath;
        this.sheetFills = sheetFills;
    }

    public ExcelFill(InputStream templateInputStream, OutputStream fileOutputStream, List<SheetFill> sheetFills) {
        this.templateInputStream = templateInputStream;
        this.fileOutputStream = fileOutputStream;
        this.sheetFills = sheetFills;
    }

    public ExcelFill(String templateFilePath, String filePath, List<SheetFill> sheetFills) {
        this.templateFilePath = templateFilePath;
        this.filePath = filePath;
        this.sheetFills = sheetFills;
    }

    public ExcelFill(String templateFilePath, OutputStream fileOutputStream, List<SheetFill> sheetFills) {
        this.templateFilePath = templateFilePath;
        this.fileOutputStream = fileOutputStream;
        this.sheetFills = sheetFills;
    }

    @Data
    public static class SheetFill {

        /**
         * 第几个sheet 默认从0开始
         */
        private int sheet;

        @Getter
        private Map<String, SheetPerFill> fillData;

        public SheetFill() {
            fillData = new HashMap<>();
        }

        /**
         * 
         * @param prefix
         *            前缀
         * @param data
         *            数据
         * @return
         */
        public <VO extends Serializable> SheetFill addFillData(String prefix, List<VO> data) {
            return this.addFillData(prefix, DirectionEnum.VERTICAL, data);
        }

        /**
         * 
         * @param prefix
         *            前缀
         * @param direction
         *            填充方向
         * @param data
         *            数据
         * 
         * @return
         */
        public <VO extends Serializable> SheetFill addFillData(String prefix, DirectionEnum direction, List<VO> data) {
            SheetPerFill perFill = SheetPerFill.builder().prefix(prefix).direction(direction).data(data).build();
            fillData.put(perFill.getPrefix(), perFill);
            return this;
        }

        public SheetFill addFillData(Object data) {
            SheetPerFill perFill = SheetPerFill.builder().prefix(data.getClass().getName()).data(data).build();
            fillData.put(perFill.getPrefix(), perFill);
            return this;
        }

    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    public static class SheetPerFill {

        private String prefix;
        /**
         * 数据填充方向 默认垂直填充
         */
        @Builder.Default
        private DirectionEnum direction = DirectionEnum.VERTICAL;

        /**
         * 要填充的数据
         */
        private Object data;

    }

    public enum DirectionEnum {
        /**
         * 垂直填充
         */
        VERTICAL,

        /**
         * 横向填充
         */
        HORIZONTAL;

        DirectionEnum() {}

        public WriteDirectionEnum getDirection() {
            if (this.equals(HORIZONTAL)) {
                return WriteDirectionEnum.HORIZONTAL;
            }
            return WriteDirectionEnum.VERTICAL;
        }

    }

}