package com.coral.base.common.excel.convert;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.coral.base.common.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
    public LocalDate convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty excelContentProperty,
                                       GlobalConfiguration globalConfiguration) {
        return DateTimeUtil.excel2LocalDate(cellData.getNumberValue().doubleValue());
    }

    @Override
    public WriteCellData<LocalDate> convertToExcelData(LocalDate localDate, ExcelContentProperty excelContentProperty,
                                                       GlobalConfiguration globalConfiguration) {
        String value = DateTimeUtil.dateTime2Excel(LocalDateTime.of(localDate, LocalTime.of(0, 0, 0))) + "";
        return new WriteCellData(CellDataTypeEnum.NUMBER, value);
    }
}
