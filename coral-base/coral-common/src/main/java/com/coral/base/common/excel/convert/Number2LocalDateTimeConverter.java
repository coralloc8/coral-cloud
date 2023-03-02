package com.coral.base.common.excel.convert;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.coral.base.common.DateTimeUtil;

import java.time.LocalDateTime;

/**
 * @author huss
 */
public class Number2LocalDateTimeConverter implements Converter<LocalDateTime> {

    @Override
    public Class supportJavaTypeKey() {
        return LocalDateTime.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.NUMBER;
    }

    @Override
    public LocalDateTime convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty excelContentProperty,
                                           GlobalConfiguration globalConfiguration) throws Exception {
        return DateTimeUtil.excel2LocalDateTime(cellData.getNumberValue().doubleValue());
    }

    @Override
    public WriteCellData<LocalDateTime> convertToExcelData(LocalDateTime localDateTime, ExcelContentProperty excelContentProperty,
                                                           GlobalConfiguration globalConfiguration) throws Exception {
        return new WriteCellData(CellDataTypeEnum.NUMBER, DateTimeUtil.dateTime2Excel(localDateTime) + "");
    }
}
