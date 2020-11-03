package com.example.spring.common.excel;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.alibaba.excel.annotation.ExcelProperty;
import com.example.spring.common.excel.model.ExcelWrite;
import com.example.spring.common.excel.model.IModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: excel写入测试
 * @author: huss
 * @time: 2020/10/26 16:00
 */
public class WriteExcelTest {

    public static void main(String[] args) {

        String fileName = "C:\\Users\\huss\\Desktop\\test.xlsx";

        List<WarehouseVO> datas = IntStream.rangeClosed(0, 10).mapToObj(e -> {
            WarehouseVO warehouseVO = new WarehouseVO();
            warehouseVO.setMerchantName("商家11111111111111111111111111111111" + (e + 1));
            warehouseVO.setStoreId(UUID.randomUUID().toString());
            warehouseVO.setAddress(UUID.randomUUID().toString());
            warehouseVO.setOuterWarehouseName("仓库名" + (e + 1));
            warehouseVO.setStoreFullName("店全名" + (e + 1));
            warehouseVO.setStoreName("222222222222222" + (e + 1));
            warehouseVO.setWarehouseNo("ware" + (e + 1));
            return warehouseVO;
        }).collect(Collectors.toList());

        ExcelWrite.SheetWrite sheetWrite = new ExcelWrite.SheetWrite();
        sheetWrite.setSheet(0);
        sheetWrite.setHeadVoClass(WarehouseVO.class);
        sheetWrite.setSheetName("前置仓列表");
        sheetWrite.setData(datas);

        ExcelWrite.SheetWrite sheetWrite2 = new ExcelWrite.SheetWrite();
        sheetWrite2.setSheet(1);
        sheetWrite2.setHeadVoClass(WarehouseVO.class);
        sheetWrite2.setSheetName("前置仓列表2");
        sheetWrite2.setData(datas);

        ExcelWrite excelWrite = new ExcelWrite();
        excelWrite.setFilePath(fileName);
        excelWrite.setSheetWrites(Arrays.asList(sheetWrite, sheetWrite2));

        ExcelWriteHandler excelWriteHandler = new ExcelWriteHandler();
        excelWriteHandler.write(excelWrite);

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    // @HeadRowHeight(25)
    // @ContentRowHeight(25)
    // @ColumnWidth(25)
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
