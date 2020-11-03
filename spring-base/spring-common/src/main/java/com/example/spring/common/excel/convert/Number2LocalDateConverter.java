package com.example.spring.common.excel.convert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.example.spring.common.DateTimeUtil;

/**
 * @author huss
 */
public class Number2LocalDateConverter implements Converter<LocalDate> {

    @Override
    public Class supportJavaTypeKey() {
        return LocalDate.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.NUMBER;
    }

    @Override
    public LocalDate convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty,
        GlobalConfiguration globalConfiguration) throws Exception {
        return DateTimeUtil.excel2LocalDate(cellData.getNumberValue().doubleValue());
    }

    @Override
    public CellData convertToExcelData(LocalDate localDate, ExcelContentProperty excelContentProperty,
        GlobalConfiguration globalConfiguration) throws Exception {
        return new CellData(DateTimeUtil.dateTime2Excel(LocalDateTime.of(localDate, LocalTime.of(0, 0, 0))));
    }
}
