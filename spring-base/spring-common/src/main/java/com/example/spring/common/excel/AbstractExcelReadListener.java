package com.example.spring.common.excel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;

import com.example.spring.common.excel.model.IModel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @description: excel 读取监听器
 * @author: huss
 * @time: 2020/10/26 15:48
 */
@Slf4j
public abstract class AbstractExcelReadListener<T extends IModel> extends AnalysisEventListener<T> {

    @Setter
    @Getter
    private int everyReadMaxLine = 50;

    @Getter
    private List<T> list = new ArrayList<>();

    private AtomicInteger atomicInteger = new AtomicInteger(1);

    @Override
    public void invoke(T data, AnalysisContext analysisContext) {
        if (filterData(data)) {
            list.add(data);
        }
        if (atomicInteger.getAndIncrement() == getEveryReadMaxLine()) {
            saveData(Collections.unmodifiableList(getList()));
            atomicInteger.set(1);
            list.clear();
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info(">>>>读取完毕，list size:{}", getList().size());
        saveData(Collections.unmodifiableList(getList()));
    }

    /**
     * 这里会一行行的返回头
     *
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        log.info("解析到一条头数据:{}", headMap);
    }

    /**
     * 在转换异常 获取其他异常下会调用本接口。抛出异常则停止读取。如果这里不抛出异常则 继续读取下一行。
     *
     * @param exception
     * @param context
     * @throws Exception
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) {
        log.error("解析失败，但是继续解析下一行:{}", exception.getMessage());
        // 如果是某一个单元格的转换异常 能获取到具体行号
        // 如果要获取头的信息 配合invokeHeadMap使用
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException)exception;
            log.error("第{}行，第{}列解析异常", excelDataConvertException.getRowIndex(),
                excelDataConvertException.getColumnIndex());
        }
    }

    // /**
    // * 额外信息（批注、超链接、合并单元格信息读取）
    // *
    // * @param extra
    // * @param context
    // */
    // @Override
    // public void extra(CellExtra extra, AnalysisContext context) {
    // log.info("读取到了一条额外信息:{}", extra);
    // switch (extra.getType()) {
    // case COMMENT:
    // log.info("额外信息是批注,在rowIndex:{},columnIndex;{},内容是:{}", extra.getRowIndex(), extra.getColumnIndex(),
    // extra.getText());
    // break;
    // case HYPERLINK:
    // if ("Sheet1!A1".equals(extra.getText())) {
    // log.info("额外信息是超链接,在rowIndex:{},columnIndex;{},内容是:{}", extra.getRowIndex(), extra.getColumnIndex(),
    // extra.getText());
    // } else if ("Sheet2!A1".equals(extra.getText())) {
    // log.info(
    // "额外信息是超链接,而且覆盖了一个区间,在firstRowIndex:{},firstColumnIndex;{},lastRowIndex:{},lastColumnIndex:{},"
    // + "内容是:{}",
    // extra.getFirstRowIndex(), extra.getFirstColumnIndex(), extra.getLastRowIndex(),
    // extra.getLastColumnIndex(), extra.getText());
    // } else {
    // log.error("Unknown hyperlink!");
    // }
    // break;
    // case MERGE:
    // log.info("额外信息是超链接,而且覆盖了一个区间,在firstRowIndex:{},firstColumnIndex;{},lastRowIndex:{},lastColumnIndex:{}",
    // extra.getFirstRowIndex(), extra.getFirstColumnIndex(), extra.getLastRowIndex(),
    // extra.getLastColumnIndex());
    // break;
    // default:
    // }
    // }

    /**
     * 数据过滤条件 默认不过滤
     *
     * @param data
     * @return
     */
    protected boolean filterData(T data) {
        return true;
    }

    /**
     * 发送数据
     */
    protected abstract void saveData(List<T> list);
}