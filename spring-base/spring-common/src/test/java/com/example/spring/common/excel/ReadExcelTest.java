package com.example.spring.common.excel;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import com.alibaba.excel.annotation.ExcelProperty;
import com.example.spring.common.excel.model.ExcelRead;
import com.example.spring.common.excel.model.IModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: excel读取测试
 * @author: huss
 * @time: 2020/10/26 16:00
 */
public class ReadExcelTest {

    public static void main(String[] args) {

        String fileName = "C:\\Users\\huss\\Desktop\\仓初始化数据.xlsx";

        InputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(new File(fileName)));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        ExcelReadHandler excelReadHandler = new ExcelReadHandler();

        ExcelRead.SheetRead<WarehouseVO> sheetRead =
            new ExcelRead.SheetRead<>(0, WarehouseVO.class, new WarehouseExcelReadListener());

        excelReadHandler.read(new ExcelRead(inputStream, Arrays.asList(sheetRead)));

    }

    public static class WarehouseExcelReadListener extends AbstractExcelReadListener<WarehouseVO> {

        @Override
        protected void saveData(List<WarehouseVO> list) {
            System.out.println(">>>>>list:" + list);
        }

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WarehouseVO implements IModel {

        @ExcelProperty("商家")
        private String merchantName;

        @ExcelProperty("门店ID(store_id)")
        private String storeId;

        @ExcelProperty("店名(name)")
        private String storeFullName;

        @ExcelProperty("分店名(subname)")
        private String storeName;

        @ExcelProperty("前置仓ID")
        private String warehouseNo;

        @ExcelProperty("前置仓名称")
        private String outerWarehouseName;

        @ExcelProperty("前置仓地址")
        private String address;

    }
}
