package com.example.spring.common.excel;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.example.spring.common.CollectionUtil;
import com.example.spring.common.excel.model.ExcelRead;
import com.example.spring.common.excel.model.IModel;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * @description: excel读取
 * @author: huss
 * @time: 2020/10/26 15:13
 */
@Slf4j
public class ExcelReadHandler {

    /**
     * 读取excel
     * 
     * @param excelRead
     * @param <T>
     */
    public <T extends IModel> void read(@NonNull ExcelRead excelRead) {
        long start = System.currentTimeMillis();
        InputStream inputStream = excelRead.getInputStream();
        List<ExcelRead.SheetRead> sheetReads = excelRead.getSheetReads();

        if (Objects.isNull(inputStream) || CollectionUtil.isBlank(sheetReads)) {
            log.error("not allowed null param, excelRead：{}", excelRead);
            return;
        }

        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(inputStream).build();

            List<ReadSheet> sheets = sheetReads.stream().map(sheet ->

            EasyExcel.readSheet(sheet.getSheet())
                // 头部设置
                .head(sheet.getHeadVoClass())
                // 监听器
                .registerReadListener(sheet.getListener())
                //
                .build()

            ).collect(Collectors.toList());
            excelReader.read(sheets);
        } finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }

        long end = System.currentTimeMillis();
        log.info("##### excel 读取完成,耗时:{}", (end - start));
    }

}
