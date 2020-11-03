package com.example.spring.common.excel.convert;

import java.time.LocalDate;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.example.spring.common.DateTimeUtil;

/**
 * @author huss
 */
public class String2LocalDateConverter implements Converter<LocalDate> {

    @Override
    public Class supportJavaTypeKey() {
        return LocalDate.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public LocalDate convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty,
        GlobalConfiguration globalConfiguration) throws Exception {
        return LocalDate.from(DateTimeUtil.parse(cellData.getStringValue(), DateTimeUtil.DATE_FORMAT));
    }

    @Override
    public CellData convertToExcelData(LocalDate localDate, ExcelContentProperty excelContentProperty,
        GlobalConfiguration globalConfiguration) throws Exception {
        return new CellData(DateTimeUtil.formatDate(localDate));
    }
}
