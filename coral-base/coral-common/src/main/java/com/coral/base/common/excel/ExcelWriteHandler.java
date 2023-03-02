package com.coral.base.common.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.alibaba.excel.write.style.row.AbstractRowHeightStyleStrategy;
import com.coral.base.common.StringUtils;
import com.coral.base.common.excel.model.ExcelWrite;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;

import java.util.List;
import java.util.Objects;

/**
 * @description: 写excel
 * @author: huss
 * @time: 2020/10/29 15:02
 */
@Slf4j
public class ExcelWriteHandler {

    /**
     * excel默认高度
     */
    private final static int DEFAULT_HEIGHT = 25;
    /**
     * excel默认宽度
     */
    private final static int DEFAULT_WIDTH = 25;

    public ExcelWriteHandler() {
    }

    public ExcelWriteHandler(List<WriteHandler> handlers) {
        this.handlers = handlers;
    }

    @Getter
    private List<WriteHandler> handlers;

    /**
     * @see https://www.yuque.com/easyexcel/doc/write#1bea3540
     *
     *      <1> 日期、数字或者自定义格式转换
     *      <p>
     *      使用{@link com.alibaba.excel.annotation.ExcelProperty}配合使用注解
     *      {@link com.alibaba.excel.annotation.format.DateTimeFormat}、
     *      {@link com.alibaba.excel.annotation.format.NumberFormat}或者自定义注解
     *      </p>
     *
     *      <2>定义列宽、行高
     *      <p>
     *      使用注解{@link ColumnWidth}、{@link HeadRowHeight}、{@link ContentRowHeight}指定宽度或高度
     *      </p>
     *      <p />
     *
     *
     */

    /**
     * 写入excel
     *
     * @param excelWrite
     */
    public void write(@NonNull ExcelWrite excelWrite) {
        long start = System.currentTimeMillis();

        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcel.write(excelWrite.getFilePath()).build();

            ExcelWriter finalExcelWriter = excelWriter;

            excelWrite.getSheetWrites().forEach(write -> {

                ExcelWriterSheetBuilder excelWriterSheetBuilder;

                if (StringUtils.isBlank(write.getSheetName())) {
                    excelWriterSheetBuilder = EasyExcel.writerSheet(write.getSheet());
                } else {
                    excelWriterSheetBuilder = EasyExcel.writerSheet(write.getSheet(), write.getSheetName());
                }
                excelWriterSheetBuilder.head(write.getHeadVoClass())
                        // 设置默认样式
                        .registerWriteHandler(this.createDefaultStyle())
                        // 设置sheet
                        .registerWriteHandler(new CustomSheetWriteHandler())
                        // 设置row
                        .registerWriteHandler(new CustomRowHeightHandler())
                        // 自动宽度
                        .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy());

                if (Objects.nonNull(getHandlers()) && !getHandlers().isEmpty()) {
                    getHandlers().forEach(e -> excelWriterSheetBuilder.registerWriteHandler(e));
                }

                finalExcelWriter.write(write.getData(), excelWriterSheetBuilder.build());
            });

        } finally {
            if (excelWriter != null) {
                // 千万别忘记finish 会帮忙关闭流
                excelWriter.finish();
            }
        }

        long end = System.currentTimeMillis();
        log.info("##### excel 写入完成,耗时:{}", (end - start));

    }

    @Slf4j
    public static class CustomRowHeightHandler extends AbstractRowHeightStyleStrategy {

        @Override
        protected void setHeadColumnHeight(Row row, int relativeRowIndex) {
            row.setHeightInPoints(DEFAULT_HEIGHT);
        }

        @Override
        protected void setContentColumnHeight(Row row, int relativeRowIndex) {
            row.setHeightInPoints(DEFAULT_HEIGHT);
        }
    }

    @Slf4j
    public static class CustomSheetWriteHandler implements SheetWriteHandler {

        @Override
        public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

        }

        @Override
        public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
            log.info("第{}个Sheet写入成功。", writeSheetHolder.getSheetNo());

            // // 区间设置 第一列第一行和第二行的数据。由于第一行是头，所以第一、二行的数据实际上是第二三行
            // CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(1, 2, 0, 0);
            // DataValidationHelper helper = writeSheetHolder.getSheet().getDataValidationHelper();
            // DataValidationConstraint constraint = helper.createExplicitListConstraint(new String[] {"测试1", "测试2"});
            // DataValidation dataValidation = helper.createValidation(constraint, cellRangeAddressList);
            // writeSheetHolder.getSheet().addValidationData(dataValidation);

            // 设置默认行高和列宽
            writeSheetHolder.getSheet().setDefaultRowHeight((short) DEFAULT_HEIGHT);
            writeSheetHolder.getSheet().setDefaultColumnWidth(DEFAULT_WIDTH);
            writeSheetHolder.getSheet().setDefaultRowHeightInPoints(DEFAULT_HEIGHT);

        }
    }

    @Slf4j
    private static class CustomCellWriteHandler implements CellWriteHandler {

        @Override
        public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
                                     Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {

        }

        @Override
        public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell,
                                    Head head, Integer relativeRowIndex, Boolean isHead) {

        }

    }

    /**
     * excel默认样式
     *
     * @return
     */
    private HorizontalCellStyleStrategy createDefaultStyle() {
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 头部背景色设置
        headWriteCellStyle.setFillForegroundColor(IndexedColors.CORAL.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 14);
        headWriteCellStyle.setWriteFont(headWriteFont);

        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        // 背景白色
        // contentWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());

        // 设置边框样式
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);

        // 设置 自动换行
        // contentWriteCellStyle.setWrapped(true);
        // 设置 垂直居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 左右居中
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);

        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short) 12);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

        return horizontalCellStyleStrategy;
    }
}
