package com.coral.base.common.excel.convert;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.coral.base.common.DateTimeUtil;

import java.time.LocalTime;

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
    public LocalTime convertToJavaData(ReadCellData cellData, ExcelContentProperty excelContentProperty,
                                       GlobalConfiguration globalConfiguration) throws Exception {
        return LocalTime.from(DateTimeUtil.parse(cellData.getStringValue(), DateTimeUtil.TIME_FORMAT));
    }

    @Override
    public WriteCellData<LocalTime> convertToExcelData(LocalTime localTime, ExcelContentProperty excelContentProperty,
                                                       GlobalConfiguration globalConfiguration) throws Exception {
        return new WriteCellData(DateTimeUtil.formatTime(localTime));
    }
}
