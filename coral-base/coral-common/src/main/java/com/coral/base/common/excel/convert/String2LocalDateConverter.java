package com.coral.base.common.excel.convert;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.coral.base.common.DateTimeUtil;

import java.time.LocalDate;

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
    public LocalDate convertToJavaData(ReadCellData cellData, ExcelContentProperty excelContentProperty,
                                       GlobalConfiguration globalConfiguration) throws Exception {
        return LocalDate.from(DateTimeUtil.parse(cellData.getStringValue(), DateTimeUtil.DATE_FORMAT));
    }

    @Override
    public WriteCellData<LocalDate> convertToExcelData(LocalDate localDate, ExcelContentProperty excelContentProperty,
                                                       GlobalConfiguration globalConfiguration) throws Exception {
        return new WriteCellData(DateTimeUtil.formatDate(localDate));
    }
}
