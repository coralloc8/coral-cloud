package com.example.spring.common.excel;

import java.util.Collection;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import com.example.spring.common.StringUtils;
import com.example.spring.common.excel.model.ExcelFill;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * @description: excel按模板填充
 * @author: huss
 * @time: 2020/10/29 20:15
 */
@Slf4j
public class ExcelFillHandler {

    /**
     * excel填充
     * 
     * @param excelFill
     */
    public void fill(@NonNull ExcelFill excelFill) {
        ExcelWriter excelWriter = null;
        try {
            excelWriter =
                EasyExcel.write(excelFill.getFilePath()).withTemplate(excelFill.getTemplateFilePath()).build();

            ExcelWriter finalExcelWriter = excelWriter;
            excelFill.getSheetFills().forEach(sheet -> {
                WriteSheet writeSheet = EasyExcel.writerSheet(sheet.getSheet()).build();

                sheet.getFillData().entrySet().forEach(entry -> {
                    ExcelFill.SheetPerFill sheetPerFill = entry.getValue();

                    FillConfig fillConfig =
                        FillConfig.builder().direction(sheetPerFill.getDirection().getDirection()).build();
                    if (sheetPerFill.getData() instanceof Collection) {
                        finalExcelWriter.fill(new FillWrapper(entry.getKey(), (Collection)sheetPerFill.getData()),
                            fillConfig, writeSheet);
                    } else {
                        finalExcelWriter.fill(sheetPerFill.getData(), fillConfig, writeSheet);
                    }

                });

            });

        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }

    }

}
