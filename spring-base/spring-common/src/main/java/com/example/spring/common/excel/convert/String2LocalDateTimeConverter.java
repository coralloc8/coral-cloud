package com.example.spring.common.excel.convert;

import java.time.LocalDateTime;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.example.spring.common.DateTimeUtil;

/**
 * @author huss
 */
public class String2LocalDateTimeConverter implements Converter<LocalDateTime> {

    @Override
    public Class supportJavaTypeKey() {
        return LocalDateTime.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public LocalDateTime convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty,
        GlobalConfiguration globalConfiguration) throws Exception {
        return LocalDateTime.from(DateTimeUtil.parse(cellData.getStringValue(), DateTimeUtil.DATETIME_FORMAT));
    }

    @Override
    public CellData convertToExcelData(LocalDateTime localDateTime, ExcelContentProperty excelContentProperty,
        GlobalConfiguration globalConfiguration) throws Exception {
        return new CellData(DateTimeUtil.formatDateTime(localDateTime));
    }
}
