package com.coral.base.common.excel.convert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.coral.base.common.DatePattern;
import com.coral.base.common.DateTimeUtil;

/**
 * 日期 MM/dd/yy yyyy/MM/dd
 * 
 * @author huss
 */
public class String2LocalDateMdyConverter implements Converter<LocalDate> {

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
        try {
            // MM/dd/yy
            return LocalDate.from(
                DateTimeUtil.parse(cellData.getStringValue(), DateTimeFormatter.ofPattern(DatePattern.MM_DD_YY_SLASH)));
        } catch (Exception e) {
            try {
                // 数字转日期
                return DateTimeUtil.excel2LocalDate(cellData.getNumberValue().doubleValue());
            } catch (Exception e1) {
                throw e1;
            }

        }

    }

    @Override
    public CellData convertToExcelData(LocalDate localDate, ExcelContentProperty excelContentProperty,
        GlobalConfiguration globalConfiguration) throws Exception {
        return new CellData(DateTimeUtil.format(localDate, DatePattern.MM_DD_YY_SLASH));
    }
}
