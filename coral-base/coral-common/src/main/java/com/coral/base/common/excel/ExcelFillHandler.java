package com.coral.base.common.excel;

import java.util.Collection;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import com.coral.base.common.excel.model.ExcelFill;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

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
            ExcelWriterBuilder excelWriterBuilder;
            if(StringUtils.isNotBlank(excelFill.getFilePath())) {
                excelWriterBuilder =  EasyExcel.write(excelFill.getFilePath());
            }else {
                excelWriterBuilder =  EasyExcel.write(excelFill.getFileOutputStream());
            }

            if(StringUtils.isNotBlank(excelFill.getTemplateFilePath())) {
                excelWriterBuilder.withTemplate(excelFill.getTemplateFilePath());
            } else {
                excelWriterBuilder.withTemplate(excelFill.getTemplateInputStream());
            }
            excelWriter = excelWriterBuilder.build();

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
