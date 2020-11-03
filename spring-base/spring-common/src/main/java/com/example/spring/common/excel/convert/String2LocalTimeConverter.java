package com.example.spring.common.excel.convert;

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
public class String2LocalTimeConverter implements Converter<LocalTime> {

    @Override
    public Class supportJavaTypeKey() {
        return LocalTime.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public LocalTime convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty,
        GlobalConfiguration globalConfiguration) throws Exception {
        return LocalTime.from(DateTimeUtil.parse(cellData.getStringValue(), DateTimeUtil.TIME_FORMAT));
    }

    @Override
    public CellData convertToExcelData(LocalTime localTime, ExcelContentProperty excelContentProperty,
        GlobalConfiguration globalConfiguration) throws Exception {
        return new CellData(DateTimeUtil.formatTime(localTime));
    }
}
